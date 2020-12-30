package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProvinceBiz;
import com.pro.financial.management.dto.ProvinceDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/qrcode")
    public JSONObject qrcode(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String code = request.getParameter("code");
        if (StringUtils.isEmpty(code)) {
            return result;
        }
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", "");
        return result;
    }
}
