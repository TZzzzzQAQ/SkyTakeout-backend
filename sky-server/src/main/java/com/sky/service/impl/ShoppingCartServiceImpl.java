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

    /**
     * 向用户购物车中添加商品
     *
     * @param shoppingCartDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/

    @Override
    public void addToCart(ShoppingCartDTO shoppingCartDTO) {
        //首先获取一个购物车的entity
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        //根据要添加到购物车的商品查询数据库
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getCartByCart(shoppingCart);
        if (!shoppingCarts.isEmpty()) {
            //如果数据库中存在这个商品则直接修改数量
            shoppingCarts.get(0).setNumber(shoppingCarts.get(0).getNumber() + 1);
            shoppingCartMapper.update(shoppingCarts.get(0));
        } else {
            //如果数据库中不存在这个商品，那么添加这个菜品到购物车当中
            if (shoppingCartDTO.getDishId() != null) {
                //添加一个是菜品的商品在购物车当中
                Dish dishById = dishMapper.getDishById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dishById.getName());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setAmount(dishById.getPrice());
                shoppingCart.setImage(dishById.getImage());
            } else {
                //添加一个套餐到购物车中
                Setmeal setmeal = setMealMapper.getSetMealEntityById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
            }
            //补全所有的信息
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            //插入到数据库当中
            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 获取该用户购物车中的所有数据
     *
     * @return java.util.List<com.sky.entity.ShoppingCart>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Override
    public List<ShoppingCart> getShoppingCart() {
        return shoppingCartMapper.getShoppingCart(BaseContext.getCurrentId());
    }

    /**
     * 清空购物车
     *
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Override
    public void cleanAllShoppingCart() {
        shoppingCartMapper.clean(BaseContext.getCurrentId());
    }

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/

    @Override
    public void subFromCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = shoppingCartMapper.getCartByDTO(shoppingCartDTO);
        if (shoppingCart.getNumber() > 1) {
            shoppingCart.setNumber(shoppingCart.getNumber() - 1);
            shoppingCartMapper.update(shoppingCart);
        } else {
            shoppingCartMapper.deleteCart(shoppingCart);
        }
    }
}
