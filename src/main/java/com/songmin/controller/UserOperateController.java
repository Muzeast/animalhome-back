package com.songmin.controller;

import com.songmin.model.ResultMap;
import com.songmin.model.User;
import com.songmin.service.UserOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserOperateController {
    @Autowired
    UserOperateService userOperateService;
    /**
     * 用户注册
     * */
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap<Map<String, String>> register(@RequestBody User userInfo) {
        return userOperateService.register(userInfo);
    }
    /**
     * 验证用户登陆
     * */
    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap<Map<String, Object>> login(@RequestBody User userInfo) {
        return userOperateService.verifyLogin(userInfo);
    }
    /**
     * 注销用户登陆
     * */
    @RequestMapping(value = "/logout.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<String> logout(@RequestHeader("tokenauthorization") String token) {
        return userOperateService.logout(token);
    }
    /**
     * 获取用户信息
     * */
    @RequestMapping(value = "/userInformation.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<User> userInfo(@RequestAttribute("userId") String userId) {
        return userOperateService.getUserInformation(userId);
    }

    /**
     * 获取所有用户信息
     */
    @RequestMapping(value = "allUserList.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<List<User>> allUserList(@RequestAttribute("userId") String userId) {
        return userOperateService.allUserList(userId);
    }
}
