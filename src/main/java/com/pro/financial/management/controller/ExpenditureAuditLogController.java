package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureAuditLogBiz;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenditure_audit_log")
public class ExpenditureAuditLogController {

    @Autowired
    private ExpenditureAuditLogBiz expenditureAuditLogBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditureAuditLog(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureAuditLogDto expenditureAuditLogDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureAuditLogDto.class);
        int count = expenditureAuditLogBiz.addExpenditureAuditLog(expenditureAuditLogDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
