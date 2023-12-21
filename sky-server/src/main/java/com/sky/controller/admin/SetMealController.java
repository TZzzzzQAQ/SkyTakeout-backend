package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "套餐相关接口")
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
    @CacheEvict(cacheNames = "setMealCache", key = "#setmealDTO.categoryId")
    public Result<String> insertNewSetMeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setMealService.insertNewSetMeal(setmealDTO);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return com.sky.result.Result<com.sky.vo.SetmealVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getSetMealById(@PathVariable Long id) {
        log.info("根据id:{}查询套餐", id);
        SetmealVO setmealVO = setMealService.getSetMealById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐信息
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setMealCache", allEntries = true)
    public Result<String> changeSetMealInfo(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐内容：{}", setmealDTO);
        setMealService.changeSetMealInfo(setmealDTO);
        return Result.success();
    }

    /**
     * 修改套餐状态
     *
     * @param status
     * @param id
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @PostMapping("/status/{status}")
    @ApiOperation("套餐启售、停售")
    @CacheEvict(cacheNames = "setMealCache", allEntries = true)
    public Result<String> changeSetMealStatus(@PathVariable Integer status, Long id) {
        log.info("套餐{}：启售、停售：{}", id, status);
        setMealService.changeSetMealStatus(status, id);
        return Result.success();
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    //清理所有的缓存
    @CacheEvict(cacheNames = "setMealCache", allEntries = true)
    public Result<String> deleteSetMeal(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setMealService.delete(ids);
        return Result.success();
    }
}
