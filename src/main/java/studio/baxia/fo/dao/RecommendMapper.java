package studio.baxia.fo.dao;

import org.springframework.stereotype.Repository;
import studio.baxia.fo.pojo.Recommend;
import studio.baxia.fo.util.BaseMapper;

/**
 * Created by Pan on 2016/12/20.
 */
public interface RecommendMapper extends BaseMapper<Recommend> {

    Integer updateHits(Long id);

}
