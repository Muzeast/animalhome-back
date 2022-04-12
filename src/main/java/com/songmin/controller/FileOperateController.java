package com.songmin.controller;

import com.songmin.model.ResultMap;
import com.songmin.service.FileOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileOperateController {
    @Autowired
    FileOperateService fileOperateService;

    @RequestMapping(value = "/uploadAvatar.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> uploadPhoto(@RequestParam("file")MultipartFile file,
                                                      @RequestHeader("tokenauthorization") String token) {
        return fileOperateService.uploadAvatar(file, token);
    }

    @RequestMapping(value = "/downloadAvatar.json", method = RequestMethod.GET)
    public void downloadPhoto(@RequestHeader("tokenauthorization") String token, HttpServletResponse response) throws Exception{
        fileOperateService.downloadAvatar(token, response);
    }

    @RequestMapping(value = "uploadRescuePhoto.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> uploadRescuePhoto(@RequestAttribute("userId")String userId, @RequestParam("file")MultipartFile file) {
        return fileOperateService.uploadRescuePhoto(file, userId);
    }

    @RequestMapping(value = "deleteRescuePhoto.json", method = RequestMethod.GET)
    public ResultMap<Map<String, String>> deleteRescuePhoto(@RequestAttribute("userId")String userId, @RequestParam("file")String fileName) {
        return fileOperateService.deleteRescuePhoto(userId, fileName);
    }

    @RequestMapping(value = "downloadRescuePhoto.json", method = RequestMethod.GET)
    public void downloadRescuePhotos(@RequestParam("rescueApplyId")String rescueApplyId) {
        //
    }
}
