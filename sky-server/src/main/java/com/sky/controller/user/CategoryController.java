package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "Client分类接口")
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类
     *
     * @param type
     * @return com.sky.result.Result<java.util.List < com.sky.entity.Category>>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @GetMapping("/list")
    @ApiOperation("查询分类")
    public Result<List<Category>> getCategoryByType(Integer type) {
        log.info("条件查询分类：{}", type);
        return Result.success(categoryService.list(type));
    }
}
