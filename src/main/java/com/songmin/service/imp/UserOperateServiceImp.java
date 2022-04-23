package com.songmin.service.imp;

import com.songmin.dao.UserOperateMapper;
import com.songmin.model.AuthenticApplyBean;
import com.songmin.model.ResultMap;
import com.songmin.model.UserBasicBean;
import com.songmin.service.UserOperateService;
import com.songmin.utils.HttpUtils;
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
    public ResultMap<Map<String, String>> register(UserBasicBean userInfo) {
        ResultMap<Map<String, String>> result = new ResultMap<>();
        Map<String, String> res = new HashMap<>();
        List<UserBasicBean> user = generalMapper.queryUserInfoByCount(userInfo.getCount());
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
        int ri = generalMapper.insertUserInfo(userInfo);
        if (ri == 1) {
            result.setCode(200);
        }

        return result;
    }
    @Override
    public ResultMap<Map<String, Object>> verifyLogin(UserBasicBean user) {
        ResultMap<Map<String, Object>> res = new ResultMap<>();
        Map<String, Object> result = new HashMap<>();
        List<UserBasicBean> userInfo = generalMapper.queryUserInfoByCount(user.getCount());
        if (userInfo == null || userInfo.size() < 1) {
            res.setCode(404);
            result.put("msg", "用户不存在!");
        } else {
            UserBasicBean curUser = userInfo.get(0);
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
    public ResultMap<UserBasicBean> getUserInformation(String userId) {
        ResultMap<UserBasicBean> result = new ResultMap<>();
        List<UserBasicBean> userList = generalMapper.queryUserInfoById(userId);

        if (userList == null || userList.size() < 1) {
            result.setCode(404);
            return result;
        }
        UserBasicBean user = userList.get(0);
        //擦除部分重要信息
        user.setPassword(null);
        user.setUserId(null);

        result.setCode(200);
        result.setResult(user);

        return result;
    }

    @Override
    public ResultMap<List<UserBasicBean>> allUserList(String userId) {
        ResultMap<List<UserBasicBean>> resultMap = new ResultMap<>();
        //验证用户是否为管理员
        int validateAuth = generalMapper.validateAdministrator(userId);
        if (validateAuth == 0) {
            resultMap.setCode(400);
            return resultMap;
        }
        List<UserBasicBean> userList = generalMapper.queryUserInfoById(null); //查询所有用户信息

        resultMap.setCode(200);
        resultMap.setResult(userList);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> submitAuthenticApply(AuthenticApplyBean bean) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();

        //提交已上传的图片
        Map<String, Object> params = new HashMap<>();
        params.put("userId", bean.getUserId());
        params.put("type", "authentic");
        HttpUtils.sendGet("http://127.0.0.1:18088/file/submitCachePhoto.json", params);
        int res = generalMapper.insertAuthenticApply(bean);
        if (res > 0) {
            generalMapper.updateUserBasicAuthenticStatus(bean.getUserId(), 2);
            message.put("msg", "OK");
            resultMap.setCode(200);
        } else {
            message.put("msg", "保存信息失败");
            resultMap.setCode(400);
        }
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<AuthenticApplyBean> getAuthenticApply(String userId) {
        ResultMap<AuthenticApplyBean> resultMap = new ResultMap<>();

        List<AuthenticApplyBean> applyList = generalMapper.getAuthenticApply(userId);
        if (applyList == null || applyList.size() < 1) {
            resultMap.setCode(404);
        } else {
            resultMap.setCode(200);
            resultMap.setResult(applyList.get(0));
        }

        return resultMap;
    }
}
