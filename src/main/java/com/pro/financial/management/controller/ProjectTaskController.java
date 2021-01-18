package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.biz.ProjectTaskBiz;
import com.pro.financial.management.dao.ProjectTaskRelationDao;
import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.pro.financial.management.dto.ProjectTaskDto;
import com.pro.financial.management.dto.ProjectTaskRelationDto;
import com.pro.financial.management.dto.ProjectUserDto;
import com.pro.financial.management.dto.TemplateDto;
import com.pro.financial.user.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ProjectTaskRelationDao projectTaskRelationDao;



    @RequestMapping("/addrelation")
    public JSONObject addRelation(@RequestBody JSONObject jsonInfo) {
        return projectTaskBiz.addRelation(jsonInfo);
    }

    @RequestMapping("/adduser")
    public JSONObject addUser(@RequestBody List<ProjectTaskDto> projectTaskDtos) {
        return projectTaskBiz.addUser(projectTaskDtos);
    }

    @RequestMapping("/inittask")
    public JSONObject initTask(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.initTask(projectTaskDto);
    }

    @RequestMapping("/updatetasktemplate")
    public JSONObject updateTaskTemplate(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.updateTaskTemplate(projectTaskDto);
    }

    @RequestMapping("/list1")
    public JSONObject taskList(@RequestBody ProjectTaskDto projectTaskDto) {
        return projectTaskBiz.taskList(projectTaskDto);
    }

    @RequestMapping("/gettaskrelation")
    public JSONObject getTaskRelation(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer projectId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("projectId")) ? "0" : request.getParameter("projectId"));
        if (projectId < 1) {
            result.put("code", 1001);
            result.put("msg", "参数传入有误");
            return result;
        }
        List<ProjectTaskRelationDto> projectTaskRelationDtos = projectTaskBiz.getTaskRelation(projectId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", projectTaskRelationDtos);
        return result;
    }
    @RequestMapping("/getuser")
    public JSONObject getUser(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer projectId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("projectId")) ? "0" : request.getParameter("projectId"));
        List<ProjectUserDto> projectUserDtos = projectTaskBiz.getProjectUsers(projectId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", projectUserDtos);
        return result;
    }

    @RequestMapping("/deluser")
    public JSONObject delUser(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer taskId = Integer.valueOf(request.getParameter("taskId"));
        if (taskId == null || taskId < 1) {
            result.put("code", 1001);
            result.put("msg", "参数传入有误");
            return result;
        }
        projectTaskBiz.removeById(taskId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getTask(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer projectId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("projectId")) ? "0" : request.getParameter("projectId"));
        Integer userId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("userId")) ? "0" : request.getParameter("userId"));
        Integer taskRelationId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("taskRelationId")) ? "0" : request.getParameter("taskRelationId"));

        List<ProjectTaskDto> projectTaskDtos =  projectTaskBiz.gettask(projectId, userId, taskRelationId);
        Map<String, Object> resultMap = new HashMap<>();;
        resultMap.put("task", projectTaskDtos);
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalTime = BigDecimal.ZERO;
        for (ProjectTaskDto projectTaskDto : projectTaskDtos) {
            totalTime = totalTime.add(new BigDecimal(projectTaskDto.getTakeTime()));
        }
        resultMap.put("total", total);
        resultMap.put("totalTime", totalTime);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resultMap);
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delTask(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        Integer taskRelationId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("taskRelationId")) ? "0" : request.getParameter("taskRelationId"));
        if (taskRelationId < 0 || userId == null | userId <0) {
            result.put("code",1001);
            result.put("msg","");
            return result;
        }
        projectTaskRelationDao.deleteById(taskRelationId);
        QueryWrapper<ProjectTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_relation_id", taskRelationId);
        projectTaskBiz.remove(queryWrapper);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
