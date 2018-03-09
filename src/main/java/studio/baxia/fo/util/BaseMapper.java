package studio.baxia.fo.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 集成基础的mapper
 * 
 * @author hanzhijie
 * @date 2017年1月17日
 * @param <T> 
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, tk.mybatis.mapper.common.BaseMapper<T>{

}
