package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 该函数使用pagehelper进行
     *
     * @param categoryPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @Override
    public PageResult getAllCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.getAllCategory(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 使用build创建一个Category对象，通用服用update语句
     *
     * @param status
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @Override
    public void changeCategoryStatus(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
    }

    /**
     * 修改菜品信息，复用update动态sql代码，新建category，使用BeanUtils进行对象拷贝，使用BaseContext获取操作者id信息
     *
     * @param categoryDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @Override
    public void changeCategoryInfo(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }

    /**
     * 实现删除接口，补充update信息
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/
    @Override
    public void deleteCategoryById(Long id) {
        categoryMapper.deleteCategoryById(id);
    }

    /**
     * 实现插入接口，补充update，create信息，status
     *
     * @param categoryDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/8
     **/

    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setStatus(0);
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        categoryMapper.insertCategory(category);
    }

}
