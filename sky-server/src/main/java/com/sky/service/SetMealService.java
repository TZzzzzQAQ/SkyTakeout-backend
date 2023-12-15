package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetMealService {
    /**
     * 分页查看套餐的数据
     *
     * @param setmealPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    PageResult getAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 插入新的套餐
     *
     * @param setmealDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    void insertNewSetMeal(SetmealDTO setmealDTO);

    /**
     * 根据套餐id查询数据
     *
     * @param id
     * @return com.sky.vo.SetmealVO
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    SetmealVO getSetMealById(Long id);

    void changeSetMealInfo(SetmealDTO setmealDTO);
}
