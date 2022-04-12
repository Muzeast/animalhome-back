package com.songmin.service;

import com.songmin.model.RescueApplyInfoBean;
import com.songmin.model.ResultMap;

import java.util.List;
import java.util.Map;

public interface SalvageOperateService {
    /**
     * 提交救助申请
     * @param bean
     * @return
     */
    ResultMap<Map<String, String>> rescueApply(RescueApplyInfoBean bean);

    /**
     * 获取宠物种类信息
     * @return
     */
    ResultMap<List<Map<String, String>>> petTypeInfo();
}
