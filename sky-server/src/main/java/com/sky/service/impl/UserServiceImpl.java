package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用于登录微信
     *
     * @param userLoginDTO
     * @return com.sky.entity.User
     * @author TZzzQAQ
     * @create 2023/12/18
     **/
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String string = getOpenId(userLoginDTO.getCode());
        //如果openid为空，直接报错
        if (string == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.getMyOpenId(string);
        if (user == null) {
            user = User.builder()
                    .openid(string)
                    .createTime(LocalDateTime.now())
                    .build();
            //数据库中没有，就注册在自己的数据库中
            userMapper.insert(user);
        }
        return user;
    }

    private String getOpenId(String code) {
        //将数据封装进入map中
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        //拿到传输的json字符串
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);
        //返回微信openid字段
        return jsonObject.getString("openid");
    }
}
