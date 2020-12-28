package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProvinceBiz;
import com.pro.financial.management.dto.ProvinceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private ProvinceBiz provinceBiz;

    @RequestMapping("/getcity")
    public JSONObject getCity() {
        JSONObject result = new JSONObject();
        List<ProvinceDto> provinceDtos = provinceBiz.getCity();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", provinceDtos);
        return result;
    }
}
