package com.sky.controller.category;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
