package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectBiz;
import com.pro.financial.management.biz.RemitterMethodBiz;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.RemitterMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remitter_method")
public class RemitterMethodController {

    @Autowired
    private RemitterMethodBiz remitterMethodBiz;

    @RequestMapping("/add")
    public JSONObject addRemitterMethod(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RemitterMethodDto remitterMethodDto = JSONObject.parseObject(jsonInfo.toJSONString(), RemitterMethodDto.class);
        int count = remitterMethodBiz.addRemitterMethod(remitterMethodDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
