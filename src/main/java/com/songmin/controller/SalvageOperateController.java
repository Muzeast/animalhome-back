package com.songmin.controller;

import com.songmin.model.RescueApplyInfoBean;
import com.songmin.model.ResultMap;
import com.songmin.service.SalvageOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salvage")
public class SalvageOperateController {
    @Autowired
    private SalvageOperateService salvageOperateService;

    @RequestMapping(value = "rescueApply.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> rescueApply(@RequestAttribute("userId")String userId, @RequestBody RescueApplyInfoBean bean) {
        bean.setUserId(userId);
        return salvageOperateService.rescueApply(bean);
    }

    @RequestMapping(value = "petType.json", method = RequestMethod.GET)
    public ResultMap<List<Map<String, String>>> petTypeInfo() {
        return salvageOperateService.petTypeInfo();
    }

    @RequestMapping(value = "healthGrade.json", method = RequestMethod.GET)
    public ResultMap<List<Map<String, Object>>> healthGrade() {
        return salvageOperateService.healthGrade();
    }

    @RequestMapping(value = "statusDict.json", method = RequestMethod.GET)
    public ResultMap<List<Map<String, Object>>> statusDict() {
        return salvageOperateService.statusDict();
    }

    @RequestMapping(value = "rescueApplySummary.json", method = RequestMethod.POST)
    public ResultMap<List<RescueApplyInfoBean>> rescueApplySummary(@RequestAttribute("userId")String userId,
                                                                   @RequestBody RescueApplyInfoBean bean) {
        bean.setUserId(userId);
        return salvageOperateService.rescueApplySummary(bean);
    }

    @RequestMapping(value = "rescueApplyInfoById.json", method = RequestMethod.GET)
    public ResultMap<RescueApplyInfoBean> rescueApplyInfoById(@RequestParam("applyId")String applyId) {
        return salvageOperateService.rescueApplyInfoById(applyId);
    }

    @RequestMapping(value = "updateRescueApply.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> updateRescueApply(@RequestAttribute("userId")String userId, @RequestBody RescueApplyInfoBean bean) {
        bean.setUserId(userId);
        return salvageOperateService.updateRescueApplyInfo(bean);
    }

    @RequestMapping(value = "deleteRescueApplyById.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> deleteRescueApplyById(@RequestBody RescueApplyInfoBean bean) {
        return salvageOperateService.deleteRescueApplyById(bean);
    }
}
