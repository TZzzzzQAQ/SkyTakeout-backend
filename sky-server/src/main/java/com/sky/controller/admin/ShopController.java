package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY = "SHOP_STATUS";

    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态为：{}", status.equals(StatusConstant.ENABLE) ? "营业中" : "打烊");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getStatus() {
        log.info("获取到店铺的营业");

        return Result.success((Integer) redisTemplate.opsForValue().get(KEY));
    }
}
