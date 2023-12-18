package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {
    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return com.sky.entity.User
     * @author TZzzQAQ
     * @create 2023/12/18
     **/
    public User wxLogin(UserLoginDTO userLoginDTO);
}
