package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.biz.RevenueTypeBiz;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.management.dto.RevenueTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private RevenueTypeBiz revenueTypeBiz;

    @RequestMapping("/add")
    public JSONObject addRevenue(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RevenueDto revenueDto = JSONObject.parseObject(jsonInfo.toJSONString(), RevenueDto.class);
        int count = revenueBiz.addRevenue(revenueDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
    @RequestMapping("/gettype")
    public JSONObject getRevenueType(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWords = request.getParameter("keyWords");
        List<RevenueTypeDto> revenueTypeDtos = revenueTypeBiz.getType(keyWords);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", revenueTypeDtos);
        return result;
    }


}
