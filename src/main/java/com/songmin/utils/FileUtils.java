package com.songmin.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {
    /**
     * @description 将图片文件以流的形式返回请求
     * @param filePath
     * @param response
     * @throws Exception
     */
    public static void sendFileStream(String filePath, HttpServletResponse response) throws Exception {
        String contentType = "image/";
        File file = new File(filePath);

        if (!file.exists()) {
            response.sendError(2);
            return;
        }
        //设置返回头信息
        contentType += filePath.substring(filePath.lastIndexOf(".") + 1);
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());

        byte[] buffer = new byte[1024];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
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
}
