package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {

    /**
     * 分页查看套餐数据
     *
     * @param setmealPageQueryDTO
     * @return com.github.pagehelper.Page<com.sky.vo.SetmealVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    Page<SetmealVO> getAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 插入套餐信息
     *
     * @param setmeal
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @AutoFill(OperationType.INSERT)
    void insertSetMeal(Setmeal setmeal);

    /**
     * 通过套餐id获取套餐信息
     *
     * @param id
     * @return com.sky.vo.SetmealVO
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @Select("select * from setmeal where id = #{id};")
    SetmealVO getSetMealById(Long id);

    /**
     * 更新套餐的信息
     *
     * @param setmeal
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 删除套餐
     *
     * @param setMealId
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @Delete("delete from setmeal where id = #{setMealId};")
    void deleteSetMealById(Long setMealId);

    /**
     * 根据分类的id获取套餐信息
     *
     * @param categoryId
     * @return java.util.List<com.sky.vo.SetmealVO>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @Select("select * from setmeal where category_id = ${categoryId};")
    List<SetmealVO> getSetMealByCategoryId(Long categoryId);

    @Select("select * from setmeal where id = #{setmealID};")
    Setmeal getSetMealEntityById(Long setmealId);
}
