package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RevenueTypeBiz;
import com.pro.financial.management.dto.RevenueTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revenue_type")
public class RevenueTypeController {

    @Autowired
    private RevenueTypeBiz revenueTypeBiz;

    @RequestMapping("/add")
    public JSONObject addRevenue(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RevenueTypeDto revenueTypeDto = JSONObject.parseObject(jsonInfo.toJSONString(), RevenueTypeDto.class);
        int count = revenueTypeBiz.addRevenueType(revenueTypeDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
