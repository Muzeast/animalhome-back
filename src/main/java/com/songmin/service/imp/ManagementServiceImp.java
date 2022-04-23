package com.songmin.service.imp;

import com.songmin.dao.ManagementMapper;
import com.songmin.model.*;
import com.songmin.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManagementServiceImp implements ManagementService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ManagementMapper managementMapper;

    @Override
    public ResultMap<List<Menu>> menu(String userId) {
        ResultMap<List<Menu>> result = new ResultMap<>();
        List<Menu> menuList = managementMapper.queryUserMenuAuthById(userId);
        result.setCode(200);
        result.setResult(menuList);

        return result;
    }

    @Override
    public ResultMap<Map<String, Integer>> addMenu(String userId, Menu menu) {
        ResultMap<Map<String, Integer>> resultMap = new ResultMap<>();

        //插入菜单信息
        int res = managementMapper.addMenu(menu);
        //如果不是默认菜单，则单独给管理员授权。取消，由触发器实现
        /*if (menu.getDefaultMenu() != 1) {
            String roleId = managementMapper.queryUserRoleById(userId);
            managementMapper.addRoleMenu(roleId, Arrays.asList(new String[]{menu.getMenuId()}));
        }*/
        Map<String, Integer> map = new HashMap<>();
        map.put("res", res);

        resultMap.setCode(200);
        resultMap.setResult(map);

        return resultMap;
    }

    @Override
    public ResultMap<Integer> updateMenu(List<Menu> menuList) {
        ResultMap<Integer> resultMap = new ResultMap<>();
        menuList.forEach(menu -> {
            managementMapper.updateMenu(menu);
        });

        resultMap.setCode(200);
        return resultMap;
    }

    @Override
    public ResultMap<Integer> deleteMenu(String[] menuIds) {
        ResultMap<Integer> resultMap = new ResultMap<>();

        List<String> menuIdList = Arrays.asList(menuIds);
        if (menuIdList.size() > 0) {
            managementMapper.deleteMenu(Arrays.asList(menuIds));
            managementMapper.deleteRoleMenu(null, menuIdList);
        }

        resultMap.setCode(200);

        return resultMap;
    }

    @Override
    public ResultMap<List<RoleBean>> roleList() {
        ResultMap<List<RoleBean>> resultMap = new ResultMap<>();

        List<RoleBean> roleList = managementMapper.roleList();
        resultMap.setCode(200);
        resultMap.setResult(roleList);

        return resultMap;
    }

    @Override
    public ResultMap<List<UserBasicBean>> getUserListByRole(String roleId) {
        ResultMap<List<UserBasicBean>> resultMap = new ResultMap<>();

        List<UserBasicBean> userList = managementMapper.getUserListByRole(roleId);
        resultMap.setCode(200);
        resultMap.setResult(userList);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> getRoleMenuListById(String roleId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();

        List<Map<String, Object>> roleMenuList = managementMapper.getRoleMenuListById(roleId);
        Map<String, String> map = new HashMap<>();
        roleMenuList.forEach(item -> {
            map.put(item.get("defaultMenu").toString(), item.get("menuList").toString());
        });
        resultMap.setCode(200);
        resultMap.setResult(map);

        return resultMap;
    }

    @Override
    public ResultMap<Integer> updateRoleMenu(List<RoleMenuBean> roleMenuList) {
        ResultMap<Integer> resultMap = new ResultMap<>();

        String roleId = roleMenuList.get(0).getRoleId();
        List<String> oldMenuList = new ArrayList<>();
        List<String> defaultMenuList = new ArrayList<>();
        //获取初始角色菜单信息
        List<Map<String, Object>> preRoleMenuList = managementMapper.getRoleMenuListById(roleId);
        if (preRoleMenuList != null && preRoleMenuList.size() > 0) {
            preRoleMenuList.forEach(item -> {
                if (item.get("defaultMenu").toString().equals("0")) {
                    oldMenuList.addAll(Arrays.asList(item.get("menuList").toString().split(",")));
                }
                if (item.get("defaultMenu").toString().equals("1")) {
                    defaultMenuList.addAll(Arrays.asList(item.get("menuList").toString().split(",")));
                }
            });
        }
        //待删除角色菜单ID
        List<String> deletedMenuList = new ArrayList<>(oldMenuList);
        List<String> newMenuList = new ArrayList<>();
        roleMenuList.forEach(item -> {
            newMenuList.add(item.getMenuId());
        });
        deletedMenuList.removeAll(newMenuList);
        if (deletedMenuList.size() > 0) {
            managementMapper.deleteRoleMenu(roleId, deletedMenuList);
        }
        //新增角色菜单ID
        newMenuList.removeAll(defaultMenuList);
        newMenuList.removeAll(oldMenuList);
        if (newMenuList.size() > 0) {
            managementMapper.addRoleMenu(roleId, newMenuList);
        }
        /*//获取待删除角色菜单信息
        List<RoleMenuBean> deletedRoleMenuList = new ArrayList<>(preRoleMenuList);
        deletedRoleMenuList.removeAll(roleMenuList);
        List<String> menuIds = new ArrayList<>();
        deletedRoleMenuList.forEach(roleMenu -> {
            String menuId = roleMenu.getMenuId();
            if (!menuId.equals("251B1BC7-5AFE-30DF-AEFA-CE6EBD06B8B1")) {
                menuIds.add(menuId);
            }
        });
        if (menuIds.size() > 0) {
            managementMapper.deleteRoleMenu(roleId, menuIds);
        }
        //获取待新增菜单信息
        roleMenuList.removeAll(preRoleMenuList);
        roleMenuList.forEach(roleMenu -> {
            roleMenu.setRoleMenuId(UUID.randomUUID().toString());
        });
        int res = 0;
        if (roleMenuList.size() > 0) {
            res = managementMapper.addRoleMenu(roleMenuList);
        }
        resultMap.setCode(200);
        resultMap.setResult(res);*/

        return resultMap;
    }

    @Override
    public ResultMap<Integer> updateRoleUser(List<UserRoleBean> roleUserList) {
        ResultMap<Integer> resultMap = new ResultMap<>();
        if (roleUserList.size() < 1) {
            return null;
        }
        String roleId = roleUserList.get(0).getRoleId();
        List<String> curRoleUserList = new ArrayList<>();
        roleUserList.forEach(item -> {
            curRoleUserList.add(item.getUserId());
        });
        //原有角色用户ID
        List<UserBasicBean> preRoleUserList = managementMapper.getUserListByRole(roleId);
        List<String> preUserList = new ArrayList<>();
        preRoleUserList.forEach(item -> {
            preUserList.add(item.getUserId());
        });
        //待删除用户ID
        List<String> deletedUserList = new ArrayList<>(preUserList);
        deletedUserList.removeAll(curRoleUserList);
        if (deletedUserList.size() > 0) {
            managementMapper.deleteRoleUser(roleId, deletedUserList);
        }
        int res = 0;
        curRoleUserList.removeAll(preUserList);
        if (curRoleUserList.size() > 0) {
            res = managementMapper.addRoleUser(roleId, curRoleUserList);
        }

        resultMap.setCode(200);
        resultMap.setResult(res);
        return resultMap;
    }

    public ResultMap<List<Menu>> homeMenuList(String userId) {
        ResultMap<List<Menu>> resultMap = new ResultMap<>();

        List<Menu> menuList = managementMapper.homeMenuList(userId);
        resultMap.setCode(200);
        resultMap.setResult(menuList);

        return resultMap;
    }
}
