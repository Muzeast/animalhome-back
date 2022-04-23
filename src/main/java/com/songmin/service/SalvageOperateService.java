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

    /**
     * 获取健康等级字典数据
     * @return
     */
    ResultMap<List<Map<String, Object>>> healthGrade();

    /**
     * 获取审核状态字典数据
     * @return
     */
    ResultMap<List<Map<String, Object>>> statusDict();

    /**
     * 获取用户救助申请信息汇总
     * @param bean
     * @return
     */
    ResultMap<List<RescueApplyInfoBean>> rescueApplySummary(RescueApplyInfoBean bean);

    /**
     * 根据ID获取申请信息详情
     * @param applyId
     * @return
     */
    ResultMap<RescueApplyInfoBean> rescueApplyInfoById(String applyId);

    /**
     * 修改救助申请
     * @param bean
     * @return
     */
    ResultMap<Map<String, String>> updateRescueApplyInfo(RescueApplyInfoBean bean);

    /**
     * 根据ID删除救助申请
     * @param bean
     * @return
     */
    ResultMap<Map<String, String>> deleteRescueApplyById(String applyId);
}
