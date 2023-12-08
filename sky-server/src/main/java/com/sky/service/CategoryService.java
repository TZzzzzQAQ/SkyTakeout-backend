package com.sky.service;

import com.sky.dto.CategoryDTO;
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

    /**
     * 修改菜品信息的接口
     *
     * @param categoryDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    void changeCategoryInfo(CategoryDTO categoryDTO);

    /**
     * service层删除菜品，需要补充status，update信息
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    void deleteCategoryById(Long id);

    /**
     * service层新增菜品，需要补充status，update和create信息
     *
     * @param categoryDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    void insertCategory(CategoryDTO categoryDTO);
}
