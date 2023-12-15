package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Override
    //添加transactional注解确保原子性操作
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //dish只用向数据库保存一条数据
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            //菜品的口味有多种情况，所以需要保存多次，使用lambda流式插入dish_flavor
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 分页查询所有的菜品
     *
     * @param dishPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Override
    public PageResult getAllDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.getAllDish(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除菜品，业务流程比较复杂需要判断菜品是否停售，是否没有链接任何套餐
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Override
    //原子性操作
    @Transactional
    public void deleteDishBatch(List<Long> ids) {
        //判断当前菜品是否能够删除--是否存在启售中的菜品
        for (Long id :
                ids) {
            Dish dish = dishMapper.getDishById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)) {
                //一旦存在启售的菜品直接抛出异常，让前端展示信息
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否能够删除--是否被套餐关联了
        List<Long> setIds = setMealDishMapper.getSetByMealId(ids);
        if (!setIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除菜品表中的菜品
        dishMapper.deleteByIds(ids);
        //删除风味表中的菜品
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     * 拿到dish和flavor之后将所有的结果封装在dishvo中
     *
     * @param id
     * @return com.sky.vo.DishVO
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Override
    public DishVO getDishById(Long id) {
        Dish dish = dishMapper.getDishById(id);
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getFlavorById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 修改菜品信息，普通信息直接使用update修改，flavor信息直接进行覆盖先删除再替换
     *
     * @param dishDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 通过分类id获取数据
     *
     * @param categoryId
     * @return java.util.List<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Override
    public List<DishVO> getDishByCategoryId(Long categoryId) {
        return dishMapper.getDishByCategoryId(categoryId);
    }

    @Override
    public void changeDishStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish);
    }
}
