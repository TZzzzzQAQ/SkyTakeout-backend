package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 通过购物车中的商品查询该商品
     *
     * @param shoppingCart
     * @return java.util.List<com.sky.entity.ShoppingCart>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    List<ShoppingCart> getCartByCart(ShoppingCart shoppingCart);

    /**
     * 向购物车中插入信息
     *
     * @param shoppingCart
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime});")
    void insert(ShoppingCart shoppingCart);

    /**
     * 更新商品的信息，这里只会修改商品的数量
     *
     * @param shoppingCart
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Update("update shopping_cart set number = #{number} where id = #{id};")
    void update(ShoppingCart shoppingCart);

    /**
     * 返回该用户所有购物车的信息
     *
     * @param currentId
     * @return java.util.List<com.sky.entity.ShoppingCart>
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Select("select * from shopping_cart where user_id = #{currentId};")
    List<ShoppingCart> getShoppingCart(Long currentId);

    /**
     * 清空购物车
     *
     * @param currentId
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Delete("delete from shopping_cart where user_id=#{currentId};")
    void clean(Long currentId);

    /**
     * 通过dto对象获取购物车商品信息
     *
     * @param shoppingCartDTO
     * @return com.sky.entity.ShoppingCart
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    ShoppingCart getCartByDTO(ShoppingCartDTO shoppingCartDTO);

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCart
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/22
     **/
    @Delete("delete from shopping_cart where id=#{id};")
    void deleteCart(ShoppingCart shoppingCart);

    void insertBatch(List<ShoppingCart> shoppingCartList);
}
