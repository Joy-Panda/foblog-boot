package studio.baxia.fo.dao;

import studio.baxia.fo.pojo.Guest;
import studio.baxia.fo.util.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface GuestMapper extends BaseMapper<Guest> {

    /**
     *
     *通过给定条件查询
     */
    Guest queryOneByCondition(Map<String, Object> condition);
}