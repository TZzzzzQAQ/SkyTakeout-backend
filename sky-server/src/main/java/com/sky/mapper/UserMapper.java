package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 查询数据库中是否有这个用户
     *
     * @param openid
     * @return com.sky.entity.User
     * @author TZzzQAQ
     * @create 2023/12/18
     **/
    @Select("select * from user where openid=#{openid};")
    User getMyOpenId(String openid);

    /**
     * 注册用户
     *
     * @param user
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/18
     **/
    void insert(User user);

    @Select("select * from user where id = #{userId};")
    User getUserById(Long userId);
}
