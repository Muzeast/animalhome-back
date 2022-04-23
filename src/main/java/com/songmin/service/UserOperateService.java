package com.songmin.service;

import com.songmin.model.AuthenticApplyBean;
import com.songmin.model.ResultMap;
import com.songmin.model.UserBasicBean;

import java.util.List;
import java.util.Map;

public interface UserOperateService {
    /**
     * @Desc 实现用户注册功能
     * @Param 用户注册信息
     * */
    ResultMap<Map<String, String>> register(UserBasicBean userInfo);
    /**
     * @Desc 验证用户登录
     * @Param 用户登陆信息
     * */
    ResultMap<Map<String, Object>> verifyLogin(UserBasicBean userInfo);
    /**
     * @Desc 注销用户登陆信息
     * @Param token
     * */
    ResultMap<String> logout(String token);
    /**
     * @Desc 获取用户信息
     * @Param token
     * */
    ResultMap<UserBasicBean> getUserInformation(String userId);

    /**
     * 获取所有用户信息
     * @return
     */
    ResultMap<List<UserBasicBean>> allUserList(String userId);

    /**
     * 提交用户实名认证信息
     * @param bean
     * @return
     */
    ResultMap<Map<String, String>> submitAuthenticApply(AuthenticApplyBean bean);

    ResultMap<AuthenticApplyBean> getAuthenticApply(String userId);
}
