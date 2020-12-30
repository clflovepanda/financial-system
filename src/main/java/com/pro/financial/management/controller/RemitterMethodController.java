package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RemitterMethodBiz;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dto.RemitterMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看汇款方式列表
     */
    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<RemitterMethodEntity> remitterMethodEntities = remitterMethodBiz.getList();
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("list", remitterMethodEntities);
        return result;
    }
}
