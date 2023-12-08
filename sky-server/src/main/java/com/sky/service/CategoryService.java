package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    /**
     * 该Service层用于返回所有菜品的分类数据
     *
     * @param categoryPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    PageResult getAllCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 修改菜品的状态
     *
     * @param status
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    void changeCategoryStatus(Integer status, Long id);
}
