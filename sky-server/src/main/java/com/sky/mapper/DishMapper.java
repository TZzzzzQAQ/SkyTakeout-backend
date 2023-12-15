package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @AutoFill(value = OperationType.INSERT)
        //自动填充字段
    void insert(Dish dish);

    /**
     * 使用动态sql链接两个表查询数据
     *
     * @param dishPageQueryDTO
     * @return com.github.pagehelper.Page<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    Page<DishVO> getAllDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 通过id查询dish的信息
     *
     * @param id
     * @return com.sky.entity.Dish
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Select("select * from dish where id = #{id};")
    Dish getDishById(Long id);

    /**
     * 通过id删除菜品
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/12
     **/
    @Delete("delete from dish where id = #{id};")
    void deleteById(Long id);

    /**
     * 通过菜品id删除菜品
     *
     * @param ids
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    void deleteByIds(List<Long> ids);

    /**
     * 修改菜品信息
     *
     * @param dish
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 通过分类id获取数据
     *
     * @param categoryId
     * @return java.util.List<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/13
     **/
    @Select("select * from dish where category_id = #{categoryId};")
    List<DishVO> getDishByCategoryId(Long categoryId);

    /**
     * 获取菜品信息
     *
     * @param dish
     * @return java.util.List<com.sky.vo.DishVO>
     * @author TZzzQAQ
     * @create 2023/12/16
     **/
    List<DishVO> list(Dish dish);

    /**
     * 根据套餐id获取该套餐的所有菜品
     *
     * @param id
     * @return java.util.List<com.sky.entity.Dish>
     * @author TZzzQAQ
     * @create 2023/12/16
     **/
    @Select("select d.* from dish d left join setmeal_dish s on d.id = s.dish_id where s.setmeal_id=#{setMealId};")
    List<Dish> getDishBySetMealId(Long id);
}
