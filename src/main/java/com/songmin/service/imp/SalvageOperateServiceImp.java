package com.songmin.service.imp;

import com.songmin.dao.SalvageOperateMapper;
import com.songmin.model.RescueApplyInfoBean;
import com.songmin.model.ResultMap;
import com.songmin.service.SalvageOperateService;
import com.songmin.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalvageOperateServiceImp implements SalvageOperateService {
    @Autowired
    private SalvageOperateMapper salvageOperateMapper;

    @Override
    public ResultMap<Map<String, String>> rescueApply(RescueApplyInfoBean bean) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();

        //提交已上传的图片
        Map<String, Object> params = new HashMap<>();
        params.put("userId", bean.getUserId());
        params.put("type", "rescue");
        HttpUtils.sendGet("http://127.0.0.1:18088/file/submitCachePhoto.json", params);
        //保存记录至数据库
        int res = salvageOperateMapper.insertRescueApply(bean);
        if (res > 0) {
            message.put("msg", "OK");
            resultMap.setCode(200);
        } else {
            message.put("msg", "新增申请数据失败!");
            resultMap.setCode(400);
        }
        resultMap.setResult(message);

        return resultMap;
    }

    @Override
    public ResultMap<List<Map<String, String>>> petTypeInfo() {
        ResultMap<List<Map<String, String>>> resultMap = new ResultMap<>();
        List<Map<String, String>> res = salvageOperateMapper.petTypeInfo();

        if (res != null && res.size() > 0) {
            resultMap.setCode(200);
            resultMap.setResult(res);
        } else {
            resultMap.setCode(400);
        }

        return resultMap;
    }

    @Override
    public ResultMap<List<Map<String, Object>>> healthGrade() {
        ResultMap<List<Map<String, Object>>> resultMap = new ResultMap<>();
        List<Map<String, Object>> res = salvageOperateMapper.healthGrade();

        if (res != null && res.size() > 0) {
            resultMap.setCode(200);
            resultMap.setResult(res);
        } else {
            resultMap.setCode(400);
        }

        return resultMap;
    }

    @Override
    public ResultMap<List<Map<String, Object>>> statusDict() {
        ResultMap<List<Map<String, Object>>> resultMap = new ResultMap<>();
        List<Map<String, Object>> res = salvageOperateMapper.statusDict();

        if (res == null || res.size() < 1) {
            resultMap.setCode(404);
        } else {
            resultMap.setCode(200);
            resultMap.setResult(res);
        }

        return resultMap;
    }

    @Override
    public ResultMap<List<RescueApplyInfoBean>> rescueApplySummary(RescueApplyInfoBean bean) {
        ResultMap<List<RescueApplyInfoBean>> resultMap = new ResultMap<>();
        List<RescueApplyInfoBean> res = salvageOperateMapper.rescueApplySummary(bean);

        if (res != null && res.size() > 0) {
            resultMap.setCode(200);
            resultMap.setResult(res);
        } else {
            resultMap.setCode(404);
        }

        return resultMap;
    }

    @Override
    public ResultMap<RescueApplyInfoBean> rescueApplyInfoById(String applyId) {
        ResultMap<RescueApplyInfoBean> resultMap = new ResultMap<>();
        RescueApplyInfoBean bean;

        if (applyId == null || "".equals(applyId)) {
            resultMap.setCode(404);
            return resultMap;
        }

        List<RescueApplyInfoBean> res = salvageOperateMapper.rescueApplyInfoById(applyId);
        if (res == null || res.size() < 1) {
            resultMap.setCode(404);
        } else {
            resultMap.setCode(200);
            resultMap.setResult(res.get(0));
        }

        return resultMap;
    }

    @Override
    public ResultMap<Map<String, String>> updateRescueApplyInfo(RescueApplyInfoBean bean) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();

        //提交已上传的图片
        Map<String, Object> params = new HashMap<>();
        params.put("userId", bean.getUserId());
        params.put("type", "rescue");
        HttpUtils.sendGet("http://127.0.0.1:18088/file/submitCachePhoto.json", params);
        //保存修改数据
        int res = salvageOperateMapper.updateRescueApply(bean);
        if (res > 0) {
            message.put("msg", "OK");
            resultMap.setCode(200);
        } else {
            message.put("msg", "修改失败");
            resultMap.setCode(400);
        }
        resultMap.setResult(message);

        return resultMap;
    }

    public ResultMap<Map<String, String>> deleteRescueApplyById(String applyId) {
        ResultMap<Map<String, String>> resultMap = new ResultMap<>();
        Map<String, String> message = new HashMap<>();

        int i = salvageOperateMapper.deleteRescueApplyById(applyId);
        if (i > 0) {
            message.put("msg", "OK");
            resultMap.setCode(200);
        } else {
            message.put("msg", "删除失败");
            resultMap.setCode(400);
        }
        resultMap.setResult(message);

        return resultMap;
    }
}
