package com.songmin.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Http工具类
 */
public class HttpUtils {
    private static RestTemplate restTemplate = new RestTemplate();

    public static String sendGet(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            sb.append("?");
            for (Map.Entry map : params.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            sb.substring(0, sb.lastIndexOf("&"));
        }
        ResponseEntity responseEntity = restTemplate.getForEntity(sb.toString(), String.class);

        return (String) responseEntity.getBody();
    }
}
