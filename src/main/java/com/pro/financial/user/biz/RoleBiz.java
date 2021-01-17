package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.user.converter.RoleDto2Entity;
import com.pro.financial.user.converter.RoleEntity2Dto;
import com.pro.financial.user.dao.DataSourceDao;
import com.pro.financial.user.dao.RoleDao;
import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.user.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
     *
     * @param roleDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int addRole(RoleDto roleDto) {
        roleDto.setCreateDatetime(new Date());
        int role = roleDao.addRole(roleDto);
        roleDao.addRolePermission(roleDto.getRoleId(), roleDto.getPermissions());
        roleDao.addRoleDataSource(roleDto.getRoleId(), roleDto.getDataSources());
        return role;
    }

    public RoleDto getRoleByRoleId(Integer roleId) {
        RoleEntity roleEntity = roleDao.getRoleByRoleId(roleId);
        return RoleEntity2Dto.instance.convert(roleEntity);
    }

    /**
     * 修改角色名字和角色对应的权限
     *
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
            //先删除之前的
            roleDao.deleRolePermission(roleDto.getRoleId());
            roleDao.addRolePermission(roleDto.getRoleId(), roleDto.getPermissions());
            if (!CollectionUtils.isEmpty(roleEntity.getDataSources())) {
                roleDao.deleRoleDataSource(roleDto.getRoleId());
                roleDao.addRoleDataSource(roleDto.getRoleId(), roleDto.getDataSources());
            }
        }


        //修改权限
        return count;
    }

    public List<DataSourceDto> getParentDataSource() {
        return dataSourceDao.getParentDataSource();
    }

    public boolean changeRoleState(Integer roleId) {
        boolean result = true;
        String state = CommonConst.common_invalid;
        try {
            RoleDto user = this.getRoleByRoleId(roleId);
            if (StringUtils.equals(CommonConst.common_invalid, user.getState())) {
                roleDao.changeRoleState(roleId, CommonConst.common_valid);
            }
            roleDao.changeRoleState(roleId, CommonConst.common_invalid);
        } catch (Exception e) {
            e.printStackTrace();
            return !result;
        }
        return result;
    }

    public List<DataSourceDto> getDataSourceByParentId(Integer parentId) {
        return dataSourceDao.getDataSourceByParentId(parentId);
    }

    public JSONObject getMenu(Integer userId) {
        Set<String> set = roleDao.getMenu(userId);
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", set);
        return result;
    }
}
