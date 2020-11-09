package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.converter.RoleDto2Entity;
import com.pro.financial.user.converter.RoleEntity2Dto;
import com.pro.financial.user.dao.DataSourceDao;
import com.pro.financial.user.dao.RoleDao;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleBiz {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private DataSourceDao dataSourceDao;

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

    /**
     * 修改角色名字和角色对应的权限
     * @param roleDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(RoleDto roleDto) {
        RoleEntity roleEntity = RoleDto2Entity.instance.convert(roleDto);
        //修改角色
        int count = 0;
        if (!CollectionUtils.isEmpty(roleEntity.getPermissions())) {
            count = roleDao.update(roleEntity);
            for (PermissionEntity permissionEntity : roleEntity.getPermissions()) {
                //先删除之前的
                roleDao.deleRolePermission(roleDto.getRoleId());
                roleDao.addRolePermission(roleDto.getRoleId(), roleDto.getPermissions());
            }
        }

        //修改权限
        return count;
    }

    public List<DataSourceDto> getParentDataSource() {
        return dataSourceDao.getParentDataSource();
    }
}
