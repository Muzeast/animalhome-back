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
    ResultMap<Map<String, String>> uploadRescuePhoto(MultipartFile file, String userId);

    /**
     * 删除已上传救助申请图片
     * @param userId
     * @param fileName
     * @return
     */
    ResultMap<Map<String, String>> deleteRescuePhoto(String userId, String fileName);

    /**
     * 确认上传救助申请图片
     * @param userId
     * @return
     */
    ResultMap<Map<String, String>> submitRescuePhoto(String userId);

    ResultMap<Map<String, String>> discardRescuePhoto(String userId);

    /**
     * 下载已上传救助申请图片
     */
    void downloadRescuePhoto(String userId, String fileName, HttpServletResponse response);
}
