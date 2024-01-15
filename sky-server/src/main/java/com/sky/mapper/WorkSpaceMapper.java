package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface WorkSpaceMapper {
    Integer getOrderStatus(Map<String, Object> map);

    @Select("select count(id) from dish where status = #{status};")
    Integer getDishCount(Map<String, Integer> map);

    @Select("select count(id) from setmeal where status = #{status};")
    Integer getSetMealCount(Map<String, Integer> map);
}
