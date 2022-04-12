package com.songmin.dao;

import com.songmin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOperateMapper {
    /**
     * @Desc 根据账号查找用户信息
     * @Params count
     * */
    List<User> queryUserInfoByCount(@Param("count") String count);
    /**
     * @Desc 根据ID查找用户信息
     * @Params userId
     */
    List<User> queryUserInfoById(@Param("userId") String userId);
    /**
     * 新增用户信息(注册)
     * */
    void insertUserInfo(@Param("user") User userInfo);
    /**
     * 修改用户基础信息
     */
    void updateUserInfo(@Param("user") User user);

    /**
     * 验证用户是否为管理员
     * @param userId
     * @return
     */
    int validateAdministrator(@Param("userId")String userId);
}
