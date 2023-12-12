package com.sky.controller.dish;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return com.sky.result.Result
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 查询获取所有的菜品信息
     *
     * @param dishPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> getAllDish(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        return Result.success(dishService.getAllDish(dishPageQueryDTO));
    }

    /**
     * 菜品的批量删除
     *
     * @param ids
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result<String> deleteDishBatch(@RequestParam List<Long> ids) {
        log.info("进行批量删除菜品，ID为：{}", ids);
        dishService.deleteDishBatch(ids);
        return Result.success();
    }

    /**
     * 根据前端返回的id查询菜品信息用于回显修改
     *
     * @param id
     * @return com.sky.result.Result<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("根据id{}查询菜品", id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品信息
     *
     * @param dishDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @PutMapping
    @ApiOperation("修改菜品")
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateDish(dishDTO);
        return Result.success();
    }
}
