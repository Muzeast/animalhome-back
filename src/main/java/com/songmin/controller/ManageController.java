package com.songmin.controller;

import com.songmin.model.*;
import com.songmin.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManageController {
    @Autowired
    private ManagementService managementService;

    @RequestMapping(value = "menu.json", method = RequestMethod.GET)
    public ResultMap<List<Menu>> menu(@RequestAttribute("userId") String userId) {
        return managementService.menu(userId);
    }

    @RequestMapping(value = "addMenu.json", method = RequestMethod.POST)
    public ResultMap<Map<String, Integer>> addMenu(@RequestAttribute("userId")String userId, @RequestBody Menu menu) {
        return managementService.addMenu(userId, menu);
    }

    @RequestMapping(value = "updateMenu.json", method = RequestMethod.POST)
    public ResultMap<Integer> updateMenu(@RequestBody List<Menu> menuList) {
        return managementService.updateMenu(menuList);
    }

    @RequestMapping(value = "deleteMenu.json",method = RequestMethod.POST)
    public ResultMap<Integer> deleteMenu(@RequestBody String[] menuIds) {
        return managementService.deleteMenu(menuIds);
    }

    @RequestMapping(value = "role.json", method = RequestMethod.GET)
    public ResultMap<List<RoleBean>> roleList() {
        return managementService.roleList();
    }

    @RequestMapping(value = "userListByRole.json", method = RequestMethod.GET)
    public ResultMap<List<UserBasicBean>> getUserListByRole(@RequestParam("roleId")String roleId) {
        return managementService.getUserListByRole(roleId);
    }

    @RequestMapping(value = "roleMenuList.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> getRoleMenuListById(@RequestParam("roleId")String roleId) {
        return managementService.getRoleMenuListById(roleId);
    }

    @RequestMapping(value = "updateRoleMenu.json", method = RequestMethod.POST)
    public ResultMap<Integer> updateRoleMenu(@RequestBody List<RoleMenuBean> roleMenuList) {
        return managementService.updateRoleMenu(roleMenuList);
    }

    @RequestMapping(value = "updateRoleUser.json", method = RequestMethod.POST)
    public ResultMap<Integer> updateRoleUser(@RequestBody List<UserRoleBean> userRoleList) {
        return managementService.updateRoleUser(userRoleList);
    }

    @RequestMapping(value = "homeMenuList.json", method = RequestMethod.GET)
    public ResultMap<List<Menu>> homeMenuList(@RequestAttribute("userId")String userId) {
        return managementService.homeMenuList(userId);
    }
}
