package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.UserBiz;
import com.pro.financial.user.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping("/add")
    public JSONObject addUser(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        UserDto user = jsonInfo.getJSONObject("user").toJavaObject(UserDto.class);
        if (StringUtils.isEmpty(user.getUsername())) {
            result.put("code", 1001);
            result.put("msg", "请输入用户名!");
            return result;
        }
        if (StringUtils.isEmpty(user.getMobile())) {
            result.put("code", 1001);
            result.put("msg", "请输入电话号!");
            return result;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            result.put("code", 1001);
            result.put("msg", "请输入密码!");
            return result;
        }
        if (user.getDepId() == null || user.getDepId() < 1) {
            result.put("code", 1001);
            result.put("msg", "请选择部门!");
            return result;
        }
        boolean isRegist = userBiz.isRegist(user.getMobile());
        if (isRegist) {
            result.put("code", 1002);
            result.put("msg", "该电话已经注册!");
            return result;
        }
        if (CollectionUtils.isEmpty(user.getRoleId())) {
            result.put("code", 1001);
            result.put("msg", "请选择角色!");
            return result;
        }
        userBiz.addUser(user, user.getRoleId());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
    @RequestMapping("/list")
    public JSONObject userList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String username = request.getParameter("username");
        String mobile = request.getParameter("mobile");
        String role = request.getParameter("role");
        String state = request.getParameter("state");
        String depId = request.getParameter("depId");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Integer limit = StringUtils.isEmpty(request.getParameter("limit")) ? null : Integer.parseInt(request.getParameter("limit"));
        Integer offset = StringUtils.isEmpty(request.getParameter("offset")) ? null : Integer.parseInt(request.getParameter("offset"));
        List<UserDto> userDtos = userBiz.userList(username, mobile, role, state, depId, startDt, endDt, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", userDtos);
        return result;
    }
    @RequestMapping("/update")
    public JSONObject updateUser(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        UserDto user = jsonInfo.getJSONObject("user").toJavaObject(UserDto.class);
        if (user.getUserId() == null || user.getUserId() < 1) {
            result.put("code", 1001);
            result.put("msg", "未查询到用户");
            return result;
        }
        boolean isRegist = userBiz.isRegist(user.getMobile());
        if (isRegist) {
            result.put("code", 1002);
            result.put("msg", "该电话已经注册!");
            return result;
        }
        userBiz.update(user);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/get")
    public JSONObject getUserByUserId(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String uid = request.getParameter("userId");
        int userId = 0;
        try {
            userId = Integer.parseInt(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userId < 1) {
            result.put("code", 1003);
            result.put("msg", "用户id有误");
            return result;
        }
        UserDto userDto = userBiz.getUserById(userId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", userDto);

        return result;
    }
    @RequestMapping("/changeuserstate")
    public JSONObject changeUserState(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String uid = request.getParameter("userId");
        int userId = 0;
        try {
            userId = Integer.parseInt(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userId < 1) {
            result.put("code", 1003);
            result.put("msg", "用户id有误");
            return result;
        }
        boolean success = userBiz.changeUserState(userId);
        if (success) {
            result.put("code", 0);
            result.put("msg", "");
        } else {
            result.put("code", 1001);
            result.put("msg", "修改用户状态失败");
        }


        return result;
    }
}
