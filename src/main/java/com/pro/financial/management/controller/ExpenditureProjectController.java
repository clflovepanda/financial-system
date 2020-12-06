package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureProjectBiz;
import com.pro.financial.management.dto.ExpenditureProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenditure_project")
public class ExpenditureProjectController {

    @Autowired
    private ExpenditureProjectBiz expenditureProjectBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditureProject(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureProjectDto expenditureProjectDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureProjectDto.class);
        int count = expenditureProjectBiz.addExpenditureProject(expenditureProjectDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
