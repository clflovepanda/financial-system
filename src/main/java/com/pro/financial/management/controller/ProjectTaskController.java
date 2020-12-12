package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectTaskBiz;
import com.pro.financial.management.dto.ProjectTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 工时记录表 前端控制器
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@RestController
@RequestMapping("/api/task")
public class ProjectTaskController {

    @Autowired
    private ProjectTaskBiz projectTaskBiz;

    @RequestMapping("/addrelation")
    public JSONObject addRelation(@RequestBody JSONObject jsonInfo) {
        return projectTaskBiz.addRelation(jsonInfo);
    }

    @RequestMapping("/adduser")
    public JSONObject addUser(@RequestBody List<ProjectTaskDto> projectTaskDtos) {
        return projectTaskBiz.addUser(projectTaskDtos);
    }

    @RequestMapping("/initTask")
    public JSONObject initTask(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.initTask(projectTaskDto);
    }

    @RequestMapping("/updateTaskTemplate")
    public JSONObject updateTaskTemplate(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.updateTaskTemplate(projectTaskDto);
    }

    @RequestMapping("/list")
    public JSONObject taskList(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.taskList(projectTaskDto);
    }




}
