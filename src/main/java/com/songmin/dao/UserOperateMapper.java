package com.songmin.dao;

import com.songmin.model.AuthenticApplyBean;
import com.songmin.model.UserBasicBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOperateMapper {
    /**
     * @Desc 根据账号查找用户信息
     * @Params count
     * */
    List<UserBasicBean> queryUserInfoByCount(@Param("count") String count);
    /**
     * @Desc 根据ID查找用户信息
     * @Params userId
     */
    List<UserBasicBean> queryUserInfoById(@Param("userId") String userId);
    /**
     * 新增用户信息(注册)
     * */
    int insertUserInfo(@Param("user") UserBasicBean userInfo);
    /**
     * 修改用户基础信息
     */
    void updateUserInfo(@Param("user") UserBasicBean user);

    /**
     * 验证用户是否为管理员
     * @param userId
     * @return
     */
    int validateAdministrator(@Param("userId")String userId);

    /**
     * 新增用户实名申请信息
     * @param bean
     * @return
     */
    int insertAuthenticApply(@Param("bean")AuthenticApplyBean bean);

    /**
     * 修改用户实名信息
     * @param userId
     * @param status
     * @return
     */
    int updateUserBasicAuthenticStatus(@Param("userId")String userId, @Param("status")int status);

    List<AuthenticApplyBean> getAuthenticApply(@Param("userId")String userId);
}
