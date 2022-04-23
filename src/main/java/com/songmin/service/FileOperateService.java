package com.songmin.service;

import com.songmin.model.ResultMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface FileOperateService {
    /**
     * @desc 实现用户头像上传功能
     * @param file,userId
     * */
    ResultMap<Map<String, String>> uploadAvatar(MultipartFile file, String userId);
    /**
     * @desc 实现用户头像下载
     * @param userId
     */
    void downloadAvatar(String userId, HttpServletResponse response);

    /**
     * 上传救助申请图片
     * @param file
     * @param userId
     * @return
     */
    ResultMap<Map<String, String>> cacheUploadPhoto(String userId, String type, MultipartFile file);

    /**
     * 删除已上传救助申请图片
     * @param userId
     * @param fileName
     * @return
     */
    ResultMap<Map<String, String>> cacheDeletePhoto(String userId, String type, String fileName);

    /**
     * 确认上传救助申请图片
     * @param userId
     * @return
     */
    ResultMap<Map<String, String>> submitCachePhoto(String userId, String type);

    ResultMap<Map<String, String>> discardCachePhoto(String userId, String type);

    /**
     * 下载已上传救助申请图片
     */
    void downloadPhoto(String userId, String type, String fileName, HttpServletResponse response);
}
