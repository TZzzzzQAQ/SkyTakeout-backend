package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 通过传入的ids的List删除口味
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    void deleteByDishIds(List<Long> ids);

    /**
     * 根据菜品id查询口味
     *
     * @param id
     * @return java.util.List<com.sky.entity.DishFlavor>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Select("select * from dish_flavor where dish_id = #{id};")
    List<DishFlavor> getFlavorById(Long id);
}
