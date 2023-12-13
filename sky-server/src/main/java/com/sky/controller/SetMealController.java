package com.sky.controller;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api("套餐相关接口")
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    /**
     * 分页查询套餐的数据
     *
     * @param setmealPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @GetMapping("/page")
    @ApiOperation("分页查询套餐数据")
    public Result<PageResult> getAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐数据：{}", setmealPageQueryDTO);
        PageResult pageResult = setMealService.getAllSetMeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> insertNewSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setMealService.insertNewSetMeal(setmealDTO);
        return Result.success();
    }
}
