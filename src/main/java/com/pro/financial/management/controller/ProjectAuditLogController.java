package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectAuditLogBiz;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project_audit_log")
public class ProjectAuditLogController {

    @Autowired
    private ProjectAuditLogBiz projectAuditLogBiz;

    @RequestMapping("/add")
    public JSONObject addProjectAuditLog(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ProjectAuditLogDto projectAuditLogDto = JSONObject.parseObject(jsonInfo.toJSONString(), ProjectAuditLogDto.class);
        int count = projectAuditLogBiz.addProjectAuditLog(projectAuditLogDto);
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
