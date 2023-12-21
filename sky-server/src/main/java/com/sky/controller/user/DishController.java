package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Api(tags = "Client菜品浏览接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id获取菜品的信息
     *
     * @param categoryId
     * @return com.sky.result.Result<java.util.List < com.sky.vo.DishVO>>
     * @author TZzzQAQ
     * @create 2023/12/21
     **/
    @GetMapping("/list")
    public Result<List<DishVO>> getDishByCategoryId(Long categoryId) {
        log.info("根据分类id获取菜品的信息：{}", categoryId);
        String key = "dish_" + categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        }

        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        list = dishService.getDishDetailsByDish(dish);
        redisTemplate.opsForValue().set(key, list);
        return Result.success(list);
    }
}
