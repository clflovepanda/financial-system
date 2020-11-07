package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.converter.RoleEntity2Dto;
import com.pro.financial.user.dao.RoleDao;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleBiz {

    @Autowired
    private RoleDao roleDao;

    public List<RoleDto> getRole() {
        List<RoleDto> roleDtos = null;
        List<RoleEntity> roleEntities = roleDao.getRole();
        roleDtos = JSONObject.parseArray(JSONObject.toJSONString(roleEntities), RoleDto.class);
        return roleDtos;
    }

    /**
     * 添加角色和角色所对应的权限
     * @param roleDto
     * @return
     */
    public int addRole(RoleDto roleDto) {
        roleDto.setCreateDatetime(new Date());
        int role = roleDao.addRole(roleDto);
        roleDao.addRolePermission(roleDto.getRoleId(), roleDto.getPermissions());
        return role;
    }

    public RoleDto getRoleByRoleId(Integer roleId) {
        RoleEntity roleEntity = roleDao.getRoleByRoleId(roleId);
        return RoleEntity2Dto.instance.convert(roleEntity);
    }
}
