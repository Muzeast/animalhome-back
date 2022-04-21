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

    @ApiOperation("上传救助申请图片（缓存）")
    @RequestMapping(value = "uploadRescuePhoto.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> uploadRescuePhoto(@RequestAttribute("userId")String userId, @RequestParam("file")MultipartFile file) {
        return fileOperateService.uploadRescuePhoto(file, userId);
    }

    @ApiOperation("删除救助申请图片（缓存）")
    @RequestMapping(value = "deleteRescuePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> deleteRescuePhoto(@RequestAttribute("userId")String userId, @RequestParam("file")String fileName) {
        return fileOperateService.deleteRescuePhoto(userId, fileName);
    }

    @ApiOperation("提交救助申请图片")
    @RequestMapping(value = "submitRescuePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> submitRescuePhoto(@RequestParam("userId")String userId) {
        return fileOperateService.submitRescuePhoto(userId);
    }

    @ApiOperation("撤销救助申请图片")
    @RequestMapping(value = "discardRescuePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> discardRescuePhoto(@RequestAttribute("userId")String userId) {
        return fileOperateService.discardRescuePhoto(userId);
    }

    @ApiOperation("下载救助申请图片")
    @RequestMapping(value = "downloadRescuePhoto.json", method = RequestMethod.GET)
    public void downloadRescuePhotos(@RequestAttribute("userId")String userId, @RequestParam("fileName")String fileName, HttpServletResponse response) {
        fileOperateService.downloadRescuePhoto(userId, fileName, response);
    }
}
