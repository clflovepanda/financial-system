package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.RoleBiz;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.RoleDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping("/get")
    public JSONObject getRole() {
        JSONObject result = new JSONObject();
        List<RoleDto> roles = roleBiz.getRole();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", roles);
        return result;
    }

    /**
     * 添加角色和对应的权限
     * @param jsonInfo
     * @return
     */
    @RequestMapping("/add")
    public JSONObject addRole(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RoleDto roleDto = jsonInfo.getJSONObject("role").toJavaObject(RoleDto.class);
        roleBiz.addRole(roleDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 根据角色id获取角色信息
     * @param request
     * @return
     */
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

    /**
     * 修改角色 和角色权限
     * @param jsonInfo
     * @return
     */
    @RequestMapping("/update")
    public JSONObject updateRole(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RoleDto roleDto = jsonInfo.toJavaObject(RoleDto.class);
        String roleName = roleDto.getRoleName();
        Integer roleId = roleDto.getRoleId();
        if (roleId == null || roleId < 1) {
            result.put("code", 1001);
            result.put("msg", "角色Id为空!");
            return result;
        }
        if (StringUtils.isEmpty(roleName)) {
            result.put("code", 1001);
            result.put("msg", "角色名字为空!");
            return result;
        }
        if (CollectionUtils.isEmpty(roleDto.getPermissions())) {
            result.put("code", 1001);
            result.put("msg", "角色权限为空!");
            return result;
        }
        int count = roleBiz.update(roleDto);
        if (count == 0) {
            result.put("code", 2001);
            result.put("msg", "更新失败");
        } else {
            result.put("code", 0);
            result.put("msg", "");
        }
        return result;
    }

    /**
     * 获取1级数据源
     * @return
     */
    @RequestMapping("/getparentdatasource")
    public JSONObject getDataSource() {
        JSONObject result = new JSONObject();
        List<DataSourceDto> dataSourceDtos = roleBiz.getParentDataSource();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", dataSourceDtos);
        return result;
    }

    /**
     * 修改角色冻结/启用状态
     * @param request
     * @return
     */
    @RequestMapping("/changerolestate")
    public JSONObject changeRoleState(HttpServletRequest request) {
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
        boolean success = roleBiz.changeRoleState(roleId);
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
