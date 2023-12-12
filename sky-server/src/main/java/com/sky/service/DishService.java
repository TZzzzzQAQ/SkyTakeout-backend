package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult getAllDish(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishBatch(List<Long> ids);
}
