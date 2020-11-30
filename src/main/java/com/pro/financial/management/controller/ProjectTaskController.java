package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectTaskBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 工时记录表 前端控制器
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/task")
public class ProjectTaskController {

    @Autowired
    private ProjectTaskBiz projectTaskBiz;

    @RequestMapping("/addrelation")
    public JSONObject addRelation(@RequestBody JSONObject jsonInfo) {
        return projectTaskBiz.addRelation(jsonInfo);
    }

}
