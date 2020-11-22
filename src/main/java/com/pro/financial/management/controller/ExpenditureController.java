package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.dto.ExpenditureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenditure")
public class ExpenditureController {

    @Autowired
    private ExpenditureBiz expenditureBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditure(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
        int count = expenditureBiz.addExpenditure(expenditureDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
