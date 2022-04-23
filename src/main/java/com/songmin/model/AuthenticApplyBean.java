package com.songmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 实名认证&请求信息
 */
@Getter
@Setter
public class AuthenticApplyBean {
    private String applyId; //申请ID
    private String userId; //用户ID
    private String realName; //真实名字
    private String idNumber; //身份证号
    private String idPhotoFront; //身份证正面照
    private String idPhotoRear; //身份证背面照
    private String personalPhoto; //个人照
    private String address; //居住地址
    private String contact; //联系方式
    private int auditStatus; //审核状态
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime; //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime; //修改时间
}
