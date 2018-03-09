package studio.baxia.fo.service;

import studio.baxia.fo.pojo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/10.
 */
public interface SearchService {
    //文章
    List searchByParam(String param);

    //文章
    List<Article> searchArticleByParam(String param);

    //友链
    List<Friendlink> searchFriendlinkByParam(String param);

    //推荐文章
    List<Recommend> searchRecommendByParam(String param);

    //项目
    List<Project> searchProjectByParam(String param);

}
