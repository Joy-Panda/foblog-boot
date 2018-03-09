package studio.baxia.fo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import studio.baxia.fo.common.CommonConstant;
import studio.baxia.fo.common.PageConfig;
import studio.baxia.fo.common.PageInfoResult;
import studio.baxia.fo.dao.ArticleMapper;
import studio.baxia.fo.dao.CategoryMapper;
import studio.baxia.fo.dao.MessageMapper;
import studio.baxia.fo.dao.TagMapper;
import studio.baxia.fo.pojo.Article;
import studio.baxia.fo.pojo.Category;
import studio.baxia.fo.pojo.Message;
import studio.baxia.fo.pojo.Tag;
import studio.baxia.fo.service.IBArticleService;
import studio.baxia.fo.util.ReturnUtil;
import studio.baxia.fo.vo.ArchiveVo;
import studio.baxia.fo.vo.ArticleVo;
import studio.baxia.fo.vo.CategoryVo;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pan on 2016/12/4.
 */
@Service("articleService")
public class BArticleServiceImpl implements IBArticleService {
    @Autowired
    private CategoryMapper iCategoryDao;
    @Autowired
    private TagMapper iTagDao;
    @Autowired
    private ArticleMapper iArticleDao;
    @Autowired
    private MessageMapper iMessageDao;

    @Override
    public Integer add(ArticleVo article) {
        if (article.getStatus() == CommonConstant.ACTICLE_STATUS_BLOG) {
            article.setPubTime(new Date());
        }
        article.setCode(new SimpleDateFormat("SSSMMssddmmHHyy").format(new Date()));
        article.setTagIds(getTagIdsBy(article.getTagNames()));
        article.setInsertTime(new Date());
        Integer result = iArticleDao.insert(article);
        if (ReturnUtil.returnResult(result)) {
            return article.getId();
        } else {
            return 0;
        }

    }

    @Override
    public Integer edit(ArticleVo article) {
        Integer result = 0;
        if (article.getOnlyChangeStatus() != null
                && article.getOnlyChangeStatus() == true) {
            result = iArticleDao.updateStatus(article);
        } else {
            if (article.getStatus() == CommonConstant.ACTICLE_STATUS_BLOG) {
                article.setPubTime(new Date());
            }
            article.setTagIds(getTagIdsBy(article.getTagNames()));
            result = iArticleDao.updateByPrimaryKey(article);
        }
        if (ReturnUtil.returnResult(result)) {
            return article.getId();
        } else {
            return 0;
        }
    }

    @Override
    public Boolean deleteById(int articleId) {
        Article article = iArticleDao.selectByPrimaryKey(articleId);
        if (article != null) {
            // 获取该文章id对应的所有评论记录总数
            Integer counts = getArticleCountMessages(articleId);
            // 返回删除所有评论的文章为id的受影响行数
            Message deleteBy = new Message();
            deleteBy.setArticleId(articleId);
            Integer results = iMessageDao.delete(deleteBy);
            if (results == counts) {
                Integer result = iArticleDao.deleteByPrimaryKey(articleId);
                return ReturnUtil.returnResult(result);
            }
        }
        return false;
    }

    @Override
    public Boolean deleteTag(int tagId, int articleId) {
        Article article = iArticleDao.selectByPrimaryKey(articleId);
        if (article != null) {
            String tagIdsStr = article.getTagIds();
            // int index = tagIdsStr.indexOf((tagId + ","));
            // if (index == -1) {
            // return false;
            // } else {
            String str = tagIdsStr.replaceAll((tagId + ","), "");
            article.setTagIds(str);
            Integer result = iArticleDao.updateByPrimaryKey(article);
            return ReturnUtil.returnResult(result);
            // }
        }
        return false;
    }

    @Override
    public Article getById(int articleId) {
        Article article = iArticleDao.selectByPrimaryKey(articleId);
        updateAritcleHits(articleId);
        return article;
    }

    @Override
    public ArticleVo getVoById(int articleId) {
        ArticleVo article = iArticleDao.selectVoById(articleId);
        if (article == null) {
            return null;
        }
        updateAritcleHits(articleId);
        article.setTagNames(getAllTagNamesBy(article.getTagIds()));
        return article;
    }

    @Override
    public Map<String, Object> getVoByCode(String code, Integer articleStatus) {
        ArticleVo article = iArticleDao.selectVoByCode(code, articleStatus);
        if (article == null) {
            return null;
        }
        article.setCountMessages(getArticleCountMessages(article.getId()));
        updateAritcleHits(article.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        article.setTagNames(getAllTagNamesBy(article.getTagIds()));
        Article preArticle = iArticleDao.selectNextOrPreVoBy(article, false);
        Article nextArticle = iArticleDao.selectNextOrPreVoBy(article, true);
        map.put("currentArticle", article);
        map.put("preArticle", preArticle);
        map.put("nextArticle", nextArticle);
        return map;
    }

    @Override
    public Map<String, Object> getVoByTitle(String articleTitle) {
        // String s = urlStrParamTranscoding(articleTitle);
        ArticleVo article = iArticleDao.selectVoByTitle(articleTitle,
                CommonConstant.ACTICLE_STATUS_BLOG);
        if (article == null) {
            return null;
        }
        article.setCountMessages(getArticleCountMessages(article.getId()));
        updateAritcleHits(article.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        article.setTagNames(getAllTagNamesBy(article.getTagIds()));
        Article preArticle = iArticleDao.selectNextOrPreVoBy(article, false);
        Article nextArticle = iArticleDao.selectNextOrPreVoBy(article, true);
        map.put("currentArticle", article);
        map.put("preArticle", preArticle);
        map.put("nextArticle", nextArticle);
        return map;
    }

    @Override
    public PageInfoResult<Article> getAllBy(Integer articleStatus,
                                                   PageConfig pageConfig) {
        Article article = new Article();
        article.setStatus(articleStatus);
        PageHelper.startPage(pageConfig);
        List<Article> result = iArticleDao.selectBy(article);
        for(int i =0,len = result.size();i<len;i++){
            result.get(i).setCountMessages(getArticleCountMessages(result.get(i).getId()));
        }
        PageInfoResult<Article> pageInfoResult = new PageInfoResult(result,
                pageConfig, (int) ((Page) result).getTotal());
        return pageInfoResult;
    }

    @Override
    public PageInfoResult<ArticleVo> getAllManageBy(
            Integer articleStatus, PageConfig pageConfig) {
        Article article = new Article();
        article.setStatus(articleStatus);
        PageHelper.startPage(pageConfig);
        List<ArticleVo> result = iArticleDao.selectVoBy(article);
        PageInfoResult<ArticleVo> pageInfoResult = new PageInfoResult(result,
                pageConfig, (int) ((Page) result).getTotal());
        return pageInfoResult;
    }
    @Override
    public Map<String, Object> getAllByCategoryCode(String code, boolean status) {
        Category category = iCategoryDao.selectByCode(code, status);
        Map<String, Object> map = new HashMap<>();
        CategoryVo categoryVo = new CategoryVo();
        map.put("category", categoryVo.categor2Vo(category));
        if (category != null) {
            List<ArticleVo> result = iArticleDao.selectVoBy(new Article()
                    .setStatus(CommonConstant.ACTICLE_STATUS_BLOG)
                    .setCategoryIds(category.getId()));
            int countMessages=0,hits = 0;
            for(int i =0,len = result.size();i<len;i++){
                int messageCount = getArticleCountMessages(result.get(i).getId());
                result.get(i).setCountMessages(messageCount);
                hits+=result.get(i).getHits();
                countMessages+=result.get(i).getCountMessages();
            }
            categoryVo.setCountMessages(countMessages);
            categoryVo.setHits(hits);
            int counts = result == null ? 0 : result.size();
            categoryVo.setCounts(counts);
            map.put("listArticle", result);
            return map;
        }
        return map;
    }
    @Override
    public Map<String, Object> getAllByCategoryName(String categoryName,boolean status) {
        // String s = urlStrParamTranscoding(categoryName);
        Category category = iCategoryDao.selectByName(categoryName,status);
        Map<String, Object> map = new HashMap<>();
        CategoryVo categoryVo = new CategoryVo();
        map.put("category", categoryVo.categor2Vo(category));
        if (category != null) {
            List<ArticleVo> result = iArticleDao.selectVoBy(new Article()
                    .setStatus(CommonConstant.ACTICLE_STATUS_BLOG)
                    .setCategoryIds(category.getId()));
            int countMessages=0,hits = 0;
            for(int i =0,len = result.size();i<len;i++){
                int messageCount = getArticleCountMessages(result.get(i).getId());
                result.get(i).setCountMessages(messageCount);
                hits+=result.get(i).getHits();
                countMessages+=result.get(i).getCountMessages();
            }
            categoryVo.setCountMessages(countMessages);
            categoryVo.setHits(hits);
            int counts = result == null ? 0 : result.size();
            categoryVo.setCounts(counts);
            map.put("listArticle", result);
            return map;
        }
        return map;
    }



    @Override
    public List<Article> getAllByTagId(int tagId, Integer articleStatus) {
        List<Article> list = iArticleDao.selectBy(
                new Article().setTagIds(tagId + ",").setStatus(articleStatus));
        return list;
    }

    @Override
    public List<Article> getAllByCategoryId(int categoryId,
                                                   Integer articleStatus) {
        List<Article> list = iArticleDao.selectBy(
                new Article().setCategoryIds(categoryId).setStatus(
                        articleStatus));
        return list;
    }

    @Override
    public List<ArticleVo> getAllByTagName(String tagName) {
        Tag tag = iTagDao.selectByName(tagName);
        if (tag != null) {
            List<ArticleVo> result = iArticleDao.selectVoBy(
                    new Article().setStatus(CommonConstant.ACTICLE_STATUS_BLOG)
                            .setTagIds(tag.getId() + ","));
            for(int i =0,len = result.size();i<len;i++){
                result.get(i).setCountMessages(getArticleCountMessages(result.get(i).getId()));
            }
            return result;
        }
        return null;
    }

    @Override
    public List<ArchiveVo> getAllArchiveVo(Integer articleStatus,
                                           String archiveTypeYear, String archiveTypeYearMonth) {
        return iArticleDao.selectAllArchives(articleStatus, archiveTypeYear, archiveTypeYearMonth);
    }

    @Override
    public List<Article> getAllArchiveArticles(String name, Integer articleStatus,
                                               String archiveType) {
        List<Article> list = iArticleDao.selectArchiveArticles(articleStatus, archiveType, name);
        for(int i =0,len = list.size();i<len;i++){
            list.get(i).setCountMessages(getArticleCountMessages(list.get(i).getId()));
        }
        return list;
    }

    private int getArticleCountMessages(int articleId){
        Message message = new Message();
        message.setArticleId(articleId);
        int count = iMessageDao.selectCount(message);
        return count;
    }
    private void updateAritcleHits(int articleId){
        Article article = iArticleDao.selectByPrimaryKey(articleId);
        article.setHits(article.getHits()+1);
        iArticleDao.updateHits(article);
    }

    private String[] getAllTagNamesBy(String tagIds) {
        String[] tagIdsArr = tagIds.split(",");
        if (tagIdsArr != null && tagIdsArr[0] != "") {
            String[] tagNames = new String[tagIdsArr.length];
            List<Integer> tagIdList = new ArrayList<Integer>(tagIdsArr.length);
            for (int i = 0; i < tagIdsArr.length; i++) {
                if (tagIdsArr[i] != "") {
                    tagIdList.add(Integer.parseInt(tagIdsArr[i]));
                }
            }
            List<Tag> tags = iTagDao.selectByIds(tagIdList);
            for (int i = 0; i < tags.size(); i++) {
                tagNames[i] = tags.get(i).getName();
            }
            return tagNames;
        }
        return null;
    }

    private String getTagIdsBy(String[] tagNames) {
        if (tagNames != null) {
            StringBuilder strBuilderTagIds = new StringBuilder();
            for (int i = 0; i < tagNames.length; i++) {
                if (tagNames[i] != null && tagNames[i].trim() != "") {
                    Tag t = iTagDao.selectByName(tagNames[i]);
                    if (t != null) {
                        strBuilderTagIds.append(t.getId() + ",");
                    } else {
                        Tag newTag = new Tag(tagNames[i]);
                        Integer r = iTagDao.insert(newTag);
                        if (r > 0) {
                            strBuilderTagIds.append(newTag.getId() + ",");
                        }
                    }
                }
            }
            return strBuilderTagIds.toString();
        }
        return null;
    }



    private String urlStrParamTranscoding(String param) {
        String s = null;
        try {
            if (param.equals(new String(param.getBytes("UTF-8"), "UTF-8"))) {
                s = param;
            }
            s = new String(param.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            s = param;
        }
        return s;
    }

}
