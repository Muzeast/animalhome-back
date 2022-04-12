package com.songmin.controller;

import com.songmin.model.RescueApplyInfoBean;
import com.songmin.model.ResultMap;
import com.songmin.service.SalvageOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salvage")
public class SalvageOperateController {
    @Autowired
    private SalvageOperateService salvageOperateService;

    @RequestMapping(value = "rescueApply.json", method = RequestMethod.POST)
    public ResultMap<Map<String, String>> rescueApply(@RequestBody RescueApplyInfoBean bean) {
        return salvageOperateService.rescueApply(bean);
    }

    @RequestMapping(value = "petType.json", method = RequestMethod.GET)
    public ResultMap<List<Map<String, String>>> petTypeInfo() {
        return salvageOperateService.petTypeInfo();
    }
}
