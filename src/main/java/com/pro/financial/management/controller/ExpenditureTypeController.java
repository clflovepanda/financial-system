package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureTypeBiz;
import com.pro.financial.management.dto.ExpenditureTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenditure_type")
public class ExpenditureTypeController {

    @Autowired
    private ExpenditureTypeBiz expenditureTypeBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditureType(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureTypeDto expenditureTypeDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureTypeDto.class);
        int count = expenditureTypeBiz.addExpenditureType(expenditureTypeDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
