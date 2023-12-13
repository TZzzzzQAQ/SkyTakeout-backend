package com.sky.controller;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "分类相关接口")
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 该函数用于实现查询分类的所有数据，返回PageResult类型的数据
     *
     * @param categoryPageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @GetMapping("/page")
    @ApiOperation("分类分页查询数据")
    public Result<PageResult> getAllCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询种类数据");
        return Result.success(categoryService.getAllCategory(categoryPageQueryDTO));
    }

    /**
     * 修改菜品的状态
     *
     * @param status
     * @param status
     * @param id
     * @return com.sky.result.Result
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @PostMapping("/status/{status}")
    @ApiOperation("启用，禁用分类")
    public Result changeCategoryStatus(@PathVariable Integer status, Long id) {
        log.info("修改{}分类的状态", id);
        categoryService.changeCategoryStatus(status, id);
        return Result.success();
    }

    /**
     * 修改菜品分类的信息，不包括状态
     *
     * @param categoryDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @PutMapping
    @ApiOperation("修改菜品分类的信息")
    public Result<String> changeCategoryInfo(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改菜品分类的信息{}", categoryDTO.getId());
        categoryService.changeCategoryInfo(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id删除菜品分类
     *
     * @param id
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result<String> deleteCategoryById(Long id) {
        log.info("根据id删除分类");
        categoryService.deleteCategoryById(id);
        return Result.success();
    }

    /**
     * 插入菜品信息
     *
     * @param categoryDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类{}", categoryDTO);
        categoryService.insertCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     *
     * @param type
     * @return com.sky.result.Result<java.util.List < com.sky.entity.Category>>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> getCategoryByType(Integer type) {
        log.info("根据类型查询分类：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
