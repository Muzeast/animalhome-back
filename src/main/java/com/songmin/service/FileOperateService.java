package com.songmin.service;

import com.songmin.model.ResultMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface FileOperateService {
    /**
     * @desc 实现用户头像上传功能
     * @param file,token
     * */
    ResultMap<Map<String, String>> uploadAvatar(MultipartFile file, String token);
    /**
     * @desc 实现用户头像下载
     * @param token
     */
    void downloadAvatar(String token, HttpServletResponse response) throws Exception;

    ResultMap<Map<String, String>> uploadRescuePhoto(MultipartFile file, String userId);

    ResultMap<Map<String, String>> deleteRescuePhoto(String userId, String fileName);
}
