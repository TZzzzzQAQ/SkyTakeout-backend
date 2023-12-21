package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "Client套餐浏览接口")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private DishService dishService;

    /**
     * 根据分类的id获取套餐，并且设置缓存
     *
     * @param categoryId
     * @return com.sky.result.Result<java.util.List < com.sky.vo.SetmealVO>>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setMealCache", key = "#categoryId")
    public Result<List<SetmealVO>> getSetMealByCategoryId(Long categoryId) {
        log.info("根据分类id查询套餐：{}", categoryId);
        return Result.success(setMealService.getSetMealByCategoryId(categoryId));
    }

    /**
     * 根据套餐id获取菜品
     *
     * @param id
     * @return com.sky.result.Result<java.util.List < com.sky.vo.DishItemVO>>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> getDishBySetMealId(@PathVariable("id") Long id) {
        log.info("根据套餐id查询包含的菜品：{}", id);
        return Result.success(dishService.getDishBySetMealId(id));
    }
}
