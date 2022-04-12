package com.songmin.service.imp;

import com.songmin.dao.SalvageOperateMapper;
import com.songmin.model.RescueApplyInfoBean;
import com.songmin.model.ResultMap;
import com.songmin.service.SalvageOperateService;
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
}
