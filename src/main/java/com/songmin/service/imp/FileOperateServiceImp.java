package com.songmin.service.imp;

import com.songmin.dao.UserOperateMapper;
import com.songmin.model.ResultMap;
import com.songmin.model.User;
import com.songmin.service.FileOperateService;
import com.songmin.utils.FileUtils;
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
    public ResultMap<Map<String, String>> uploadAvatar(MultipartFile file, String userId) {
        ResultMap<Map<String, String>> result = new ResultMap<>();
        if (file.isEmpty()) {
            result.setCode(400);
            return result;
        }
        String fileBasePath = basePath + userId + "\\";
        try {
            File path = new File(fileBasePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            // 删除原有文件
            for (File f : path.listFiles()) {
                if (f.getName().contains("avatar")) {
                    f.delete();
                }
            }
            String avatarName = file.getOriginalFilename();
            avatarName = avatarName.substring(avatarName.lastIndexOf("."));
            avatarName = "avatar" + avatarName;
            File avatarFile = new File(fileBasePath + avatarName);
            file.transferTo(avatarFile);
            logger.info(file.getOriginalFilename() + "上传成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void downloadAvatar(String userId, HttpServletResponse response) {
        String avatarName = "";
        String filePath = basePath + userId + "\\";
        File file = new File(filePath);
        for (File f : file.listFiles()) {
            if (f.getName().contains("avatar")){
                avatarName = f.getName();
                break;
            }
        }
        if (avatarName == null || "".equals(avatarName)) {
            filePath += "public\\images\\photo_default.jpg";
        } else {
            filePath += avatarName;
        }

        try {
            FileUtils.sendFileStream(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultMap<Map<String, String>> uploadRescuePhoto(MultipartFile file, String userId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap();
        Map<String, String> message = new HashMap<>();
        //将上传文件缓存，等待用户确认提交
        List<String> cachedUploadFileList;

        if (file.isEmpty()) {
            message.put("msg", "上传文件为空!");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }

        //获取已缓存的待上传文件列表
        Object obj = redisTemplate.opsForHash().get("rescue_upload", userId);
        if (obj != null) {
            cachedUploadFileList = (ArrayList<String>)obj;
        } else {
            cachedUploadFileList = new ArrayList<>();
        }

        try {
            String baseFilePath = basePath + userId + "\\apply\\rescue\\";
            File basePathFile = new File(baseFilePath);
            if (!basePathFile.exists()) {
                basePathFile.mkdirs();
            }
            String filePath = baseFilePath + file.getOriginalFilename();
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
            //保存新增文件信息
            cachedUploadFileList.add(filePath);
            redisTemplate.opsForHash().put("rescue_upload", userId, cachedUploadFileList);
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
        //将删除文件信息缓存，等待用户确认
        List<String> cachedDeleteFileList;

        //获取已缓存待删除文件信息
        Object obj = redisTemplate.opsForHash().get("rescue_delete", userId);
        if (obj != null) {
            cachedDeleteFileList = (ArrayList<String>)obj;
        } else {
            cachedDeleteFileList = new ArrayList<>();
        }

        String baseFilePath = basePath + userId + "\\apply\\rescue\\" + fileName;
        //保存新增待删除文件信息
        cachedDeleteFileList.add(baseFilePath);
        redisTemplate.opsForHash().put("rescue_delete", userId, cachedDeleteFileList);
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> submitRescuePhoto(String userId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();
        List<String> cachedFileList;

        //获取缓存的上传图片
        Object obj = redisTemplate.opsForHash().get("rescue_upload", userId);
        if (obj != null) {
            //清除上传缓存信息
            redisTemplate.opsForHash().delete("rescue_upload", userId);
        }
        //获取缓存的删除图片信息
        obj = redisTemplate.opsForHash().get("rescue_delete", userId);
        if (obj != null) {
            cachedFileList = (ArrayList<String>)obj;
            File file;
            for (String fileName : cachedFileList) {
                file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
            //清除缓存信息
            redisTemplate.opsForHash().delete("rescue_delete", userId);
        }
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> discardRescuePhoto(String userId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();
        List<String> cachedFileList;

        Object obj = redisTemplate.opsForHash().get("rescue_upload", userId);
        if (obj != null) {
            cachedFileList = (ArrayList<String>)obj;
            File file;
            for (String fileName : cachedFileList) {
                file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
            redisTemplate.opsForHash().delete("rescue_upload", userId);
        }

        obj = redisTemplate.opsForHash().get("rescue_delete", userId);
        if (obj != null) {
            redisTemplate.opsForHash().delete("rescue_delete", userId);
        }

        return resultMap;
    }

    @Override
    public void downloadRescuePhoto(String userId, String fileName, HttpServletResponse response) {
        String filePath = basePath + userId + "\\apply\\rescue\\" + fileName;

        try {
            FileUtils.sendFileStream(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
