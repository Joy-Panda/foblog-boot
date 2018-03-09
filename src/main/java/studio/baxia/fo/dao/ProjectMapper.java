package studio.baxia.fo.dao;

import org.springframework.stereotype.Repository;
import studio.baxia.fo.pojo.Project;
import studio.baxia.fo.util.BaseMapper;

/**
 * Created by Pan on 2016/12/27.
 */
public interface ProjectMapper extends BaseMapper<Project> {

    Integer updateHits(Long id);

}
