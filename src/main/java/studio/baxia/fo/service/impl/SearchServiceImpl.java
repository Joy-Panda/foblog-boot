package studio.baxia.fo.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.baxia.fo.pojo.*;
import studio.baxia.fo.service.SearchService;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2018/1/10.
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    /*@Autowired
    private ArtitcleRepository artitcleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RecommendRepository recommendRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FriendlinkRepository friendlinkRepository;
    @Autowired
    private ProjectRepository projectRepository;*/
    @Autowired
    TransportClient transportClient;

    public List searchByParam(String param) {
        Map<String, List> map = new LinkedHashMap<>();
        List<Article> articles = searchArticleByParam(param);
        //map.put("articles", articles);
        List<Recommend> recommends = searchRecommendByParam(param);
        //map.put("recommends", recommends);
        List<Project> projects = searchProjectByParam(param);
        //map.put("projects", projects);
        List<Friendlink> friendlinks = searchFriendlinkByParam(param);
        //map.put("friendlinks", friendlinks);
        List result= new LinkedList<>();
        result.add(articles);
        result.add(recommends);
        result.add(friendlinks);
        result.add(projects);
        return result;
    }

    //文章
    public List<Article> searchArticleByParam(String param) {
        Map<String, Float> weightMap = new HashMap<>();
        weightMap.put("title", 10f);
        weightMap.put("summary", 5f);
        weightMap.put("content", 3f);
        return searchByParam(param, weightMap, "articles", Article.class, new Article());
    }

    //友链
    public List<Friendlink> searchFriendlinkByParam(String param) {
        Map<String, Float> weightMap = new HashMap<>();
        weightMap.put("name", 10f);
        weightMap.put("description", 5f);
        weightMap.put("webAuthorName", 3f);
        return searchByParam(param, weightMap, "friendlink", Friendlink.class, new Friendlink());
    }

    //推荐文章
    public List<Recommend> searchRecommendByParam(String param) {
        Map<String, Float> weightMap = new HashMap<>();
        weightMap.put("title", 10f);
        weightMap.put("summary", 5f);
        return searchByParam(param, weightMap, "recommend", Recommend.class, new Recommend());
    }

    //项目
    public List<Project> searchProjectByParam(String param) {
        Map<String, Float> weightMap = new HashMap<>();
        weightMap.put("name", 10f);
        weightMap.put("introduction", 5f);
        return searchByParam(param, weightMap, "project", Project.class, new Project());
    }

    private List searchByParam(String param, Map<String, Float> weightMap, String type, Class clazz, Object object) {

        List reslut = new ArrayList();
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(param);
        //queryBuilder.analyzer("ik_max_word");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style=\"color:red\">").postTags("</span>");
        for (Map.Entry<String, Float> entry : weightMap.entrySet()) {
            highlightBuilder.field(entry.getKey());
            queryBuilder.field(entry.getKey(), entry.getValue());
        }

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch()
                .setQuery(queryBuilder)
                .setFrom(0)
                .setTypes(type)
                .setSize(10)
                .highlighter(highlightBuilder)
                //.addSort("update_time", SortOrder.DESC)//按照字段排序
                .setExplain(true); // 设置是否按查询匹配度排序
        // 获取搜索的文档结果
        SearchHits searchHits = searchRequestBuilder.execute().actionGet().getHits();

        for (SearchHit hist : searchHits.getHits()) {
            log.info("{}--queryBuilder{}", type, JSON.toJSONString(hist.getSourceAsString()));
        }

        SearchHit[] hits = searchHits.getHits();
        for (int i = 0; i < hits.length; i++) {
            SearchHit hit = hits[i];
            // 将文档中的每一个对象转换json串值
            String json = hit.getSourceAsString();
            // 将json串值转换成对应的实体对象
            object = JSON.parseObject(json, clazz);
            // 获取对应的高亮域
            Map<String, HighlightField> result = hit.getHighlightFields();
            for (Map.Entry<String, HighlightField> entry : result.entrySet()) {
                // 从设定的高亮域中取得指定域
                HighlightField titleField = result.get(entry.getKey());
                if (titleField != null) {
                    // 取得定义的高亮标签
                    Text[] titleTexts = titleField.fragments();
                    // 为title串值增加自定义的高亮标签
                    String highlightText = "";
                    for (Text text : titleTexts) {
                        highlightText += text;
                    }
                    setObjectProprties(entry.getKey(), object, clazz, highlightText);
                    log.info(JSON.toJSONString(object));
                    // 将追加了高亮标签的串值重新填充到对应的对象
                    reslut.add(object);
                }
            }
        }
        return reslut;
    }

    /**
     * 利用反射给字段赋值
     *
     * @param methodProperty
     * @param object
     * @param clazz
     * @param param
     */
    private void setObjectProprties(String methodProperty, Object object, Class clazz, String param) {
        try {
            methodProperty = "set" + methodProperty.substring(0, 1).toUpperCase() + methodProperty.substring(1);
            Method mt = clazz.getMethod(methodProperty, String.class);

            mt.invoke(object, param);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
