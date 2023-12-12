package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    /**
     * 通过菜品id获取套餐的id，用于判断该菜品是否链接了套餐
     *
     * @param ids
     * @return java.util.List<java.lang.Long>
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    List<Long> getSetByMealId(List<Long> ids);
}
