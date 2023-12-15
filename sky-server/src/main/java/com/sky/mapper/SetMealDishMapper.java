package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 插入套餐
     *
     * @param setmealDishList
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    void insertBatch(List<SetmealDish> setmealDishList);

    @Select("select * from setmeal_dish where setmeal_id = #{id};")
    List<SetmealDish> getDishBySetMealId(Long id);

    @Delete("delete from setmeal_dish where setmeal_id = #{setMealId};")
    void deleteBySetMealId(Long setMealId);
}
