package com.sky.controller.category;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api("分类相关接口")
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
    public Result<PageResult> getAllCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询种类数据");
        return Result.success(categoryService.getAllCategory(categoryPageQueryDTO));
    }
}
