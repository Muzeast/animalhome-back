package com.songmin.service.imp;

import com.songmin.dao.UserOperateMapper;
import com.songmin.model.ResultMap;
import com.songmin.model.User;
import com.songmin.service.UserOperateService;
import com.songmin.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserOperateServiceImp implements UserOperateService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserOperateMapper generalMapper;
    @Override
    public ResultMap<Map<String, String>> register(User userInfo) {
        ResultMap<Map<String, String>> result = new ResultMap<>();
        Map<String, String> res = new HashMap<>();
        List<User> user = generalMapper.queryUserInfoByCount(userInfo.getCount());
        //该账号已注册
        if (user.size() > 0) {
            res.put("msg", "该账号已存在");
            result.setCode(400);
            result.setResult(res);

            return result;
        }
        String password = userInfo.getPassword();
        String userId = UUID.randomUUID().toString();
        // 使用MD5对密码进行加密
        userInfo.setPassword(Md5Utils.inputPass2FormPass(password));
        userInfo.setUserId(userId);
        userInfo.setFilePath(userId.substring(3, 6) + password.substring(8, 12));
        generalMapper.insertUserInfo(userInfo);

        return result;
    }
    @Override
    public ResultMap<Map<String, Object>> verifyLogin(User user) {
        ResultMap<Map<String, Object>> res = new ResultMap<>();
        Map<String, Object> result = new HashMap<>();
        List<User> userInfo = generalMapper.queryUserInfoByCount(user.getCount());
        if (userInfo == null || userInfo.size() < 1) {
            res.setCode(404);
            result.put("msg", "用户不存在!");
        } else {
            User curUser = userInfo.get(0);
            String password = Md5Utils.inputPass2FormPass(user.getPassword());
            if (password.equals(curUser.getPassword())) {
                res.setCode(200);
                result.put("count", curUser.getCount());
                result.put("nick_name", curUser.getNickName());
                String token = UUID.randomUUID().toString();
                result.put("token", token);
                redisTemplate.opsForValue().set(token, curUser.getUserId());
            } else {
                res.setCode(400);
                result.put("msg", "用户名或密码错误!");
            }
        }
        res.setResult(result);
        return res;
    }

    @Override
    public ResultMap<String> logout(String token) {
        ResultMap<String> result = new ResultMap<>();
        if (token != null && !"".equals(token)) {
            token = token.replaceAll("Bearer", "");
            redisTemplate.delete(token);
        }

        result.setCode(200);
        result.setResult("OK");

        return result;
    }

    @Override
    public ResultMap<User> getUserInformation(String userId) {
        ResultMap<User> result = new ResultMap<>();
        List<User> userList = generalMapper.queryUserInfoById(userId);

        if (userList == null || userList.size() < 1) {
            result.setCode(404);
            return result;
        }
        User user = userList.get(0);
        //擦除部分重要信息
        user.setPassword(null);
        user.setUserId(null);
        user.setFilePath(null);

        result.setCode(200);
        result.setResult(user);

        return result;
    }

    @Override
    public ResultMap<List<User>> allUserList(String userId) {
        ResultMap<List<User>> resultMap = new ResultMap<>();
        //验证用户是否为管理员
        int validateAuth = generalMapper.validateAdministrator(userId);
        if (validateAuth == 0) {
            resultMap.setCode(400);
            return resultMap;
        }
        List<User> userList = generalMapper.queryUserInfoById(null); //查询所有用户信息
        /*userList.forEach(user -> {
            user.setUserId(null);
        });*/
        resultMap.setCode(200);
        resultMap.setResult(userList);

        return resultMap;
    }
}
