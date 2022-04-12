package com.songmin.service;

import com.songmin.model.*;

import java.util.List;
import java.util.Map;

public interface ManagementService {
    ResultMap<List<Menu>> menu(String userId);

    /**
     * 新增菜单
     * @param userId
     * @param menu
     * @return
     */
    ResultMap<Map<String, Integer>> addMenu(String userId, Menu menu);

    /**
     * 更新菜单
     * @param menuList
     * @return
     */
    ResultMap<Integer> updateMenu(List<Menu> menuList);

    /**
     * 根据ID删除菜单
     * @param menuIds
     * @return
     */
    ResultMap<Integer> deleteMenu(String[] menuIds);

    /**
     * 获取系统角色信息
     * @return
     */
    ResultMap<List<RoleBean>> roleList();

    /**
     * 根据角色ID获取该角色下的用户
     * @param roleId
     * @return
     */
    ResultMap<List<User>> getUserListByRole(String roleId);

    /**
     * 根据角色ID获取菜单信息
     * @param roleId
     * @return
     */
    ResultMap<Map<String, String>> getRoleMenuListById(String roleId);

    /**
     * 更新角色-菜单权限信息
     * @param roleMenuList
     * @return
     */
    ResultMap<Integer> updateRoleMenu(List<RoleMenuBean> roleMenuList);

    /**
     * 更新角色-用户信息
     * @param roleUserList
     * @return
     */
    ResultMap<Integer> updateRoleUser(List<UserRoleBean> roleUserList);

    ResultMap<List<Menu>> homeMenuList(String userId);
}
