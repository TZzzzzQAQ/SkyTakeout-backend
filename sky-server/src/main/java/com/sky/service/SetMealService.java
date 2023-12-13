package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

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
}
