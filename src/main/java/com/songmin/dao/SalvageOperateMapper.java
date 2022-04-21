package com.songmin.dao;

import com.songmin.model.RescueApplyInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SalvageOperateMapper {
    List<Map<String, String>> petTypeInfo();

    int insertRescueApply(@Param("bean")RescueApplyInfoBean bean);

    List<Map<String, Object>> healthGrade();

    List<Map<String, Object>> statusDict();

    List<RescueApplyInfoBean> rescueApplySummary(@Param("bean")RescueApplyInfoBean bean);

    List<RescueApplyInfoBean> rescueApplyInfoById(@Param("applyId")String applyId);

    int updateRescueApply(@Param("bean")RescueApplyInfoBean bean);

    int deleteRescueApplyById(@Param("applyId")String applyId);
}
