package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void addToCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getCartByCart(shoppingCart);
        if (!shoppingCarts.isEmpty()) {
            shoppingCarts.get(0).setNumber(shoppingCarts.get(0).getNumber() + 1);
            shoppingCartMapper.update(shoppingCarts.get(0));
        } else {
            if (shoppingCartDTO.getDishId() != null) {
                Dish dishById = dishMapper.getDishById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dishById.getName());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setAmount(dishById.getPrice());
                shoppingCart.setImage(dishById.getImage());
            } else {
                Setmeal setmeal = setMealMapper.getSetMealEntityById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());

            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCart> getShoppingCart() {
        return shoppingCartMapper.getShoppingCart(BaseContext.getCurrentId());
    }
}
