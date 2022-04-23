package com.songmin.controller;

import com.songmin.model.ResultMap;
import com.songmin.service.FileOperateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileOperateController {
    @Autowired
    FileOperateService fileOperateService;

    @ApiOperation("上传用户头像")
    @RequestMapping(value = "uploadAvatar.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> uploadPhoto(@RequestParam("file")MultipartFile file,
                                                      @RequestAttribute("userId")String userId) {
        return fileOperateService.uploadAvatar(file, userId);
    }

    @ApiOperation("下载用户头像")
    @RequestMapping(value = "/downloadAvatar.json", method = RequestMethod.GET)
    public void downloadPhoto(@RequestAttribute("userId") String userId, HttpServletResponse response){
        fileOperateService.downloadAvatar(userId, response);
    }

    @ApiOperation("上传图片（缓存）")
    @RequestMapping(value = "cacheUploadPhoto.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> cacheUploadPhoto(@RequestAttribute("userId")String userId,
                                                            @RequestParam("type")String type,
                                                            @RequestParam("file")MultipartFile file) {
        return fileOperateService.cacheUploadPhoto(userId, type, file);
    }

    @ApiOperation("删除图片（缓存）")
    @RequestMapping(value = "cacheDeletePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> cacheDeletePhoto(@RequestAttribute("userId")String userId,
                                                           @RequestParam("type")String type,
                                                           @RequestParam("file")String fileName) {
        return fileOperateService.cacheDeletePhoto(userId, type, fileName);
    }

    @ApiOperation("提交图片信息")
    @RequestMapping(value = "submitCachePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> submitCachePhoto(@RequestParam("userId")String userId, @RequestParam("type")String type) {
        return fileOperateService.submitCachePhoto(userId, type);
    }

    @ApiOperation("撤销图片信息")
    @RequestMapping(value = "discardCachePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> discardCachePhoto(@RequestAttribute("userId")String userId, @RequestParam("type")String type) {
        return fileOperateService.discardCachePhoto(userId, type);
    }

    @ApiOperation("下载图片")
    @RequestMapping(value = "downloadPhoto.json", method = RequestMethod.GET)
    public void downloadPhoto(@RequestAttribute("userId")String userId,
                                     @RequestParam("type")String type,
                                     @RequestParam("fileName")String fileName,
                                     HttpServletResponse response) {
        fileOperateService.downloadPhoto(userId, type, fileName, response);
    }
}
