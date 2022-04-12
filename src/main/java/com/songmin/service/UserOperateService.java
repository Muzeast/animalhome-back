package com.songmin.service;

import com.songmin.model.ResultMap;
import com.songmin.model.User;

import java.util.List;
import java.util.Map;

public interface UserOperateService {
    /**
     * @Desc 实现用户注册功能
     * @Param 用户注册信息
     * */
    ResultMap<Map<String, String>> register(User userInfo);
    /**
     * @Desc 验证用户登录
     * @Param 用户登陆信息
     * */
    ResultMap<Map<String, Object>> verifyLogin(User userInfo);
    /**
     * @Desc 注销用户登陆信息
     * @Param token
     * */
    ResultMap<String> logout(String token);
    /**
     * @Desc 获取用户信息
     * @Param token
     * */
    ResultMap<User> getUserInformation(String userId);

    /**
     * 获取所有用户信息
     * @return
     */
    ResultMap<List<User>> allUserList(String userId);
}
