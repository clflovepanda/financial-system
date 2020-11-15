package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectBiz;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.management.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectBiz projectBiz;

    @RequestMapping("/add")
    public JSONObject addProject(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ProjectDto projectDto = JSONObject.parseObject(jsonInfo.toJSONString(), ProjectDto.class);
        int count = projectBiz.addProject(projectDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
