package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 向购物车中添加内容
     *
     * @param shoppingCartDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    void addToCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 获取该用户购物车的所有内容
     *
     * @return java.util.List<com.sky.entity.ShoppingCart>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    List<ShoppingCart> getShoppingCart();

    /**
     * 删除该用户购物车的所有消息
     *
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    void cleanAllShoppingCart();

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    void subFromCart(ShoppingCartDTO shoppingCartDTO);
}
