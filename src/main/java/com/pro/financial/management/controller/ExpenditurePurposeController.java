package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditurePurposeBiz;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenditure_purpose")
public class ExpenditurePurposeController {

    @Autowired
    private ExpenditurePurposeBiz expenditurePurposeBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditurePurpose(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditurePurposeDto expenditurePurposeDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditurePurposeDto.class);
        int count = expenditurePurposeBiz.addExpenditurePurpose(expenditurePurposeDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
