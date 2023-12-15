package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /**
     * 根据菜品id查询菜品信息
     *
     * @param id
     * @return com.sky.vo.DishVO
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    DishVO getDishById(Long id);

    /**
     * 更新菜品信息
     *
     * @param dishDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    void updateDish(DishDTO dishDTO);

    /**
     * 通过分类id获取数据
     *
     * @param categoryId
     * @return java.util.List<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    List<DishVO> getDishByCategoryId(Long categoryId);

    void changeDishStatus(Integer status, Long id);
}
