package com.songmin.controller;

import com.songmin.model.AuthenticApplyBean;
import com.songmin.model.ResultMap;
import com.songmin.model.UserBasicBean;
import com.songmin.service.UserOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserOperateController {
    @Autowired
    UserOperateService userOperateService;

    @ApiOperation("用户注册")
    @RequestMapping(value = "register.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap<Map<String, String>> register(@RequestBody UserBasicBean userInfo) {
        return userOperateService.register(userInfo);
    }

    @ApiOperation("验证用户登陆")
    @RequestMapping(value = "login.json", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap<Map<String, Object>> login(@RequestBody UserBasicBean userInfo) {
        return userOperateService.verifyLogin(userInfo);
    }

    @ApiOperation("注销用户登陆")
    @RequestMapping(value = "logout.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<String> logout(@RequestHeader("tokenauthorization") String token) {
        return userOperateService.logout(token);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "userInformation.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<UserBasicBean> userInfo(@RequestAttribute("userId") String userId) {
        return userOperateService.getUserInformation(userId);
    }

    @ApiOperation("获取所有用户信息")
    @RequestMapping(value = "allUserList.json", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<List<UserBasicBean>> allUserList(@RequestAttribute("userId") String userId) {
        return userOperateService.allUserList(userId);
    }

    @ApiOperation("提交实名认证信息")
    @RequestMapping(value = "submitAuthenticApply.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> submitAuthenticApply(@RequestAttribute("userId")String userId,
                                                               @RequestBody AuthenticApplyBean bean) {
        bean.setUserId(userId);
        return userOperateService.submitAuthenticApply(bean);
    }

    @ApiOperation(("获取用户提交实名认证信息"))
    @RequestMapping(value = "getAuthenticApply.json", method = RequestMethod.GET)
    public ResultMap<AuthenticApplyBean> getAuthenticApply(@RequestAttribute("userId")String userId) {
        return userOperateService.getAuthenticApply(userId);
    }
}
