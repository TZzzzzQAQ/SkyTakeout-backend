package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 分页查看套餐数据
     *
     * @param setmealPageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Override
    public PageResult getAllSetMeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setMealMapper.getAllSetMeal(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 插入新的套餐
     *
     * @param setmealDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Override
    @Transactional
    public void insertNewSetMeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = Setmeal.builder().build();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setMealMapper.insertSetMeal(setmeal);
        Long setMealId = setmeal.getId();
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        setmealDishList.forEach(setmealDish -> setmealDish.setSetmealId(setMealId));
        setMealDishMapper.insertBatch(setmealDishList);
    }

    @Override
    @Transactional
    public SetmealVO getSetMealById(Long id) {
        SetmealVO setmealVO = setMealMapper.getSetMealById(id);
        setmealVO.setSetmealDishes(setMealDishMapper.getDishBySetMealId(id));
        return setmealVO;
    }

    @Override
    @Transactional
    public void changeSetMealInfo(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.update(setmeal);
        Long setMealId = setmealDTO.getId();
        setMealDishMapper.deleteBySetMealId(setMealId);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setMealId));
        setMealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 修改套餐状态信息
     *
     * @param status
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/
    @Override
    public void changeSetMealStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                .status(status)
                .id(id)
                .build();
        setMealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/15
     **/

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id :
                ids) {
            SetmealVO setmeal = setMealMapper.getSetMealById(id);
            if (setmeal.getStatus().equals(StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        ids.forEach(setMealId -> {
            setMealMapper.deleteSetMealById(setMealId);
            setMealDishMapper.deleteBySetMealId(setMealId);
        });
    }
}
