package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
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

    /**
     * 根据套餐id获取菜品
     *
     * @param id
     * @return java.util.List<com.sky.entity.SetmealDish>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @Select("select * from setmeal_dish where setmeal_id = #{id};")
    List<SetmealDish> getDishBySetMealId(Long id);

    /**
     * 根据套餐id进行删除操作
     *
     * @param setMealId
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @Delete("delete from setmeal_dish where setmeal_id = #{setMealId};")
    void deleteBySetMealId(Long setMealId);

    /**
     * 根据套餐的id获取菜品
     *
     * @param setMealId
     * @return java.util.List<com.sky.vo.DishItemVO>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @Select("select sd.copies,s.description,s.image,sd.name from setmeal s left join setmeal_dish sd on s.id = sd.setmeal_id where sd.setmeal_id = #{setMealId};")
    List<DishItemVO> getDishItemBySetMealId(Long setMealId);
}
