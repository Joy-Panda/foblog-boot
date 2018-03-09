package studio.baxia.fo.dao;

import org.springframework.stereotype.Repository;
import studio.baxia.fo.pojo.Friendlink;
import studio.baxia.fo.util.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Pan on 2016/12/1.
 */
public interface FriendMapper extends BaseMapper<Friendlink> {

    int updateHits(Integer id);
}
