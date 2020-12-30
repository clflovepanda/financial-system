package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditurePurposeBiz;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dao.entity.ExpenditurePurposeEntity;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看支出用途列表
     */
    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<ExpenditurePurposeEntity> expenditurePurposeEntities = expenditurePurposeBiz.getList();
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("list", expenditurePurposeEntities);
        return result;
    }
}
