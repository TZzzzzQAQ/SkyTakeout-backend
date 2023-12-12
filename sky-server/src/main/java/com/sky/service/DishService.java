package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    /**
     * 保存菜品的口味
     *
     * @param dishDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 获取所有的菜品信息
     *
     * @param dishPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    PageResult getAllDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    void deleteDishBatch(List<Long> ids);
}
