package com.songmin.service.imp;

import com.songmin.dao.UserOperateMapper;
import com.songmin.model.ResultMap;
import com.songmin.model.User;
import com.songmin.service.FileOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class FileOperateServiceImp implements FileOperateService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    UserOperateMapper userOperateMapper;
    private static final Logger logger = LoggerFactory.getLogger(FileOperateServiceImp.class);
    private static final String basePath = "D:\\Projects\\Work\\流浪动物救助\\Files\\"; //文件存储基础路径

    @Override
    public ResultMap<Map<String, String>> uploadAvatar(MultipartFile file, String token) {
        ResultMap<Map<String, String>> result = new ResultMap<>();
        if (file.isEmpty()) {
            result.setCode(400);
            return result;
        }
        String userId = redisTemplate.opsForValue().get(token).toString();
        List<User> users = userOperateMapper.queryUserInfoById(userId);
        if (users == null || users.size() < 1) {
            result.setCode(404);
            return result;
        }
        User user = users.get(0);
        String fileBasePath = basePath + user.getCount() + "\\" + user.getFilePath() + "\\" + "images\\";
        try {
            File path = new File(fileBasePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            // 删除原有文件
            for (File f : path.listFiles()) {
                f.delete();
            }
            File avatarFile = new File(fileBasePath + file.getOriginalFilename());
            file.transferTo(avatarFile);
            user.setAvatar(file.getOriginalFilename());
            user.setUserId(userId);
            userOperateMapper.updateUserInfo(user);
            logger.info(file.getOriginalFilename() + "上传成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void downloadAvatar(String token, HttpServletResponse response) throws Exception {
        token = token.replaceAll("Bearer", "");
        String userId = redisTemplate.opsForValue().get(token).toString();
        List<User> users = userOperateMapper.queryUserInfoById(userId);
        if (users == null || users.size() < 1) {
            response.sendError(404);
            return;
        }
        User user = users.get(0);
        String avatar = user.getAvatar();
        String contentType = "image/";
        String fileBasePath = basePath;
        if (avatar == null || "".equals(avatar)) {
            fileBasePath += "public\\images\\photo_default.jpg";
            contentType += "jpg";
        } else {
            fileBasePath += user.getCount() + "\\" + user.getFilePath() + "\\" + "images\\" + user.getAvatar();
            contentType += avatar.substring(avatar.lastIndexOf(".") + 1);
        }
        File avatarPath = new File(fileBasePath);
        if (!avatarPath.exists()) {
            response.sendError(404);
            return;
        }
        response.setContentType(contentType);
        //response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;fileName=" + avatar);
        byte[] buffer = new byte[1024];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(avatarPath))) {
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer);
                i = bis.read(buffer);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultMap<Map<String, String>> uploadRescuePhoto(MultipartFile file, String userId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap();
        Map<String, String> message = new HashMap<>();

        if (file.isEmpty()) {
            message.put("msg", "上传文件为空!");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }
        List<User> userList = userOperateMapper.queryUserInfoById(userId);
        if (userList == null || userList.size() == 0) {
            message.put("msg", "获取用户信息失败!");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }
        User user = userList.get(0);
        try {
            String baseFilePath = basePath + user.getCount() + "\\" + user.getFilePath() + "\\apply\\";
            File basePathFile = new File(baseFilePath);
            if (!basePathFile.exists()) {
                basePathFile.mkdirs();
            }
            String filePath = baseFilePath + file.getOriginalFilename();
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> deleteRescuePhoto(String userId, String fileName) {
        ResultMap<Map<String, String>> resultMap = new ResultMap();
        Map<String, String> message = new HashMap<>();
        List<User> userList = userOperateMapper.queryUserInfoById(userId);

        if (userList == null || userList.size() == 0) {
            message.put("msg", "获取用户信息失败!");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }
        User user = userList.get(0);
        String baseFilePath = basePath + user.getCount() + "\\" + user.getFilePath() + "\\apply\\";
        File basePathFile = new File(baseFilePath);
        if (!basePathFile.exists()) {
            message.put("msg", "图片路径不存在!");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }
        for (File f : basePathFile.listFiles()) {
            if (f.getName().contains(fileName)) {
                f.delete();
            }
        }
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

}
