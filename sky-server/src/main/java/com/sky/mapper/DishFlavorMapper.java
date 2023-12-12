package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入风味
     *
     * @param flavors
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 删除菜品的同时将口味表里的连带数据一起删除
     *
     * @param dishId
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Delete("delete from dish_flavor where dish_id = #{dishId};")
    void deleteByDishId(Long dishId);
}
