package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

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

    /**
     * 修改套餐的信息
     *
     * @param setmealDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    void changeSetMealInfo(SetmealDTO setmealDTO);

    /**
     * 修改套餐的状态
     *
     * @param status
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    void changeSetMealStatus(Integer status, Long id);

    /**
     * 删除套餐
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    void delete(List<Long> ids);

    /**
     * 通过分类id获取套餐信息
     *
     * @param categoryId
     * @return java.util.List<com.sky.vo.SetmealVO>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    List<SetmealVO> getSetMealByCategoryId(Long categoryId);
}
