package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.dto.RevenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {

    @Autowired
    private RevenueBiz revenueBiz;

    @RequestMapping("/add")
    public JSONObject addRevenue(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RevenueDto revenueDto = JSONObject.parseObject(jsonInfo.toJSONString(), RevenueDto.class);
        int count = revenueBiz.addRevenue(revenueDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
