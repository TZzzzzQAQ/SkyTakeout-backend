package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "Client购物车接口")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result<String> addToCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车");
        shoppingCartService.addToCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 返回购物车中保存的所有信息
     *
     * @return com.sky.result.Result<java.util.List < com.sky.entity.ShoppingCart>>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> listShoppingCart() {
        log.info("获取购物车信息");
        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCart();
        return Result.success(shoppingCarts);
    }

    /**
     * 清空该用户的购物车的所有内容
     *
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result<String> cleanAllShoppingCart() {
        log.info("用户要求清空购物车");
        shoppingCartService.cleanAllShoppingCart();
        return Result.success();
    }

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @ApiOperation("删除购物车中的一个商品")
    @PostMapping("/sub")
    public Result<String> subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中的一个商品：{}", shoppingCartDTO);
        shoppingCartService.subFromCart(shoppingCartDTO);
        return Result.success();
    }
}
