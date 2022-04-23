package com.songmin.service.imp;

import com.songmin.dao.UserOperateMapper;
import com.songmin.model.ResultMap;
import com.songmin.service.FileOperateService;
import com.songmin.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        if (file.exists()) {
            for (File f : file.listFiles()) {
                if (f.getName().contains("avatar")){
                    avatarName = f.getName();
                    break;
                }
            }
        }
        if (avatarName == null || "".equals(avatarName)) {
            filePath = basePath + "public\\images\\avatar_default.jpg";
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
    public ResultMap<Map<String, String>> cacheUploadPhoto(String userId, String type, MultipartFile file) {
        ResultMap<Map<String, String>> resultMap = new ResultMap();
        Map<String, String> message = new HashMap<>();
        //文件存储路径
        String filePath = basePath + userId + "\\image\\" + type + "\\";
        String uploadKey = type + "_upload";
        //将上传文件缓存，等待用户确认提交
        List<String> cachedUploadFileList;

        if (file.isEmpty()) {
            message.put("msg", "文件为空");
            resultMap.setCode(400);
            resultMap.setResult(message);
            return resultMap;
        }

        //获取已缓存的待上传文件列表
        Object obj = redisTemplate.opsForHash().get(uploadKey, userId);
        if (obj != null) {
            cachedUploadFileList = (ArrayList<String>)obj;
        } else {
            cachedUploadFileList = new ArrayList<>();
        }

        try {
            File basePathFile = new File(filePath);
            if (!basePathFile.exists()) {
                basePathFile.mkdirs();
            }
            filePath += file.getOriginalFilename();
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
            //保存新增文件信息
            cachedUploadFileList.add(filePath);
            redisTemplate.opsForHash().put(uploadKey, userId, cachedUploadFileList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> cacheDeletePhoto(String userId, String type, String fileName) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();
        String fileFullPath = basePath + userId + "\\image\\" + type + "\\" + fileName;
        String deleteKey = type + "_delete";
        //删除文件列表
        List<String> cachedDeleteFileList;

        //获取已缓存待删除文件信息
        Object obj = redisTemplate.opsForHash().get(deleteKey, userId);
        if (obj != null) {
            cachedDeleteFileList = (ArrayList<String>)obj;
        } else {
            cachedDeleteFileList = new ArrayList<>();
        }

        //保存新增待删除文件信息
        cachedDeleteFileList.add(fileFullPath);
        redisTemplate.opsForHash().put(deleteKey, userId, cachedDeleteFileList);
        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> submitCachePhoto(String userId, String type) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();
        List<String> cachedFileList;
        String uploadKey = type + "_upload";
        String deleteKey = type + "_delete";

        //获取缓存的上传图片
        Object obj = redisTemplate.opsForHash().get(uploadKey, userId);
        if (obj != null) {
            //清除上传缓存信息
            redisTemplate.opsForHash().delete(uploadKey, userId);
        }
        //获取缓存的删除图片信息
        obj = redisTemplate.opsForHash().get(deleteKey, userId);
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
            redisTemplate.opsForHash().delete(deleteKey, userId);
        }

        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> discardCachePhoto(String userId, String type) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();
        List<String> cachedFileList;
        String uploadKey = type + "_upload";
        String deleteKey = type + "_delete";

        Object obj = redisTemplate.opsForHash().get(uploadKey, userId);
        if (obj != null) {
            cachedFileList = (ArrayList<String>)obj;
            File file;
            for (String fileName : cachedFileList) {
                file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
            redisTemplate.opsForHash().delete(uploadKey, userId);
        }

        obj = redisTemplate.opsForHash().get(deleteKey, userId);
        if (obj != null) {
            redisTemplate.opsForHash().delete(deleteKey, userId);
        }

        message.put("msg", "OK");
        resultMap.setCode(200);
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public void downloadPhoto(String userId, String type, String fileName, HttpServletResponse response) {
        String filePath = basePath + userId + "\\image\\" + type + "\\" + fileName;

        try {
            FileUtils.sendFileStream(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
