package com.songmin.dao;

import com.songmin.model.Menu;
import com.songmin.model.RoleBean;
import com.songmin.model.RoleMenuBean;
import com.songmin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManagementMapper {
    /**
     * 获取用户当前菜单权限
     */
    List<Menu> queryUserMenuAuthById(@Param("userId") String userId);

    /**
     * 通过用户ID查询角色ID
     * @param userId
     * @return
     */
    String queryUserRoleById(@Param("userId")String userId);

    /**
     * 新增菜单信息
     * @param menu
     */
    int addMenu(@Param("menu") Menu menu);

    /**
     * 修改菜单信息
     * @param menu
     * @return
     */
    Integer updateMenu(@Param("menu") Menu menu);

    /**
     * 删除菜单
     * @param menuIds
     * @return
     */
    Integer deleteMenu(@Param("list") List<String> menuIds);

    /**
     * 新增角色-菜单权限
     * @param
     */
    Integer addRoleMenu(@Param("roleId")String roleId, @Param("list")List<String> menuList);

    /**
     * 获取系统角色信息
     * @return
     */
    List<RoleBean> roleList();

    /**
     * 获取制定角色下的用户信息
     * @param roleId
     * @return
     */
    List<User> getUserListByRole(@Param("roleId")String roleId);

    /**
     * 通过角色ID获取菜单信息
     * @param roleId
     * @return
     */
    List<Map<String, Object>> getRoleMenuListById(@Param("roleId")String roleId);

    /**
     * 删除指定角色下的菜单信息
     * @param roleId
     * @param menuIds
     * @return
     */
    int deleteRoleMenu(@Param("roleId")String roleId, @Param("list")List<String> menuIds);

    /**
     * 新增指定角色下的菜单
     * @param roleId
     * @param userIds
     * @return
     */
    int addRoleUser(@Param("roleId")String roleId, @Param("list")List<String> userIds);

    /**
     * 删除指定角色下的菜单
     * @param roleId
     * @param userIds
     * @return
     */
    int deleteRoleUser(@Param("roleId")String roleId, @Param("list")List<String> userIds);

    /**
     * 获取用户主页可访问菜单信息
     * @param userId
     * @return
     */
    List<Menu> homeMenuList(@Param("userId")String userId);
}
