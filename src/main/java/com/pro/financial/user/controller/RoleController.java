package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.RoleBiz;
import com.pro.financial.user.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleBiz roleBiz;

    @RequestMapping("/get")
    public JSONObject getRole() {
        JSONObject result = new JSONObject();
        List<RoleDto> roles = roleBiz.getRole();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", roles);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addRole(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RoleDto roleDto = jsonInfo.getJSONObject("role").toJavaObject(RoleDto.class);
        System.out.println(roleDto.toString());
        roleBiz.addRole(roleDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getbyroleid")
    public JSONObject getByRoleId(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String roleIdStr = request.getParameter("roleId");
        Integer roleId = null;
        try {
            roleId = Integer.parseInt(roleIdStr);
        } catch (Exception e) {
            roleId = 0;
        }
        if (roleId < 1) {
            result.put("code", 1001);
            result.put("msg", "角色Id为空!");
            return result;
        }
        RoleDto roleDto = roleBiz.getRoleByRoleId(roleId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", roleDto);
        return result;
    }

}
