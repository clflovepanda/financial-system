package com.pro.financial.user.biz;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.user.converter.RoleDto2Entity;
import com.pro.financial.user.converter.RoleEntity2Dto;
import com.pro.financial.user.dao.DataSourceDao;
import com.pro.financial.user.dao.PermissionDao;
import com.pro.financial.user.dao.RoleDao;
import com.pro.financial.user.dao.entity.DataSourceEntity;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.user.dto.UserDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleBiz {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private DataSourceDao dataSourceDao;
    @Autowired
    private PermissionDao permissionDao;

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
        if (!CollectionUtils.isEmpty(roleDto.getPermissions())) {
            Set<Integer> allIds = new HashSet<>();
            List<Integer> ids = roleDto.getPermissions().stream().map(permissionDto -> permissionDto.getPermissionId()).collect(Collectors.toList());
            allIds.addAll(ids);
            while (true) {
                List<PermissionEntity> permissionEntities = permissionDao.getPermissionByIds(ids);
                ids.clear();
                for (PermissionEntity permissionEntity : permissionEntities) {
                    if (permissionEntity.getParentId() != 0) {
                        allIds.add(permissionEntity.getParentId());
                        ids.add(permissionEntity.getParentId());
                    }
                }
                if (CollectionUtils.isEmpty(ids)) {
                    break;
                }
            }
            roleDao.addRolePermissionNew(roleDto.getRoleId(), allIds);
        }
        if (!CollectionUtils.isEmpty(roleDto.getDataSources())) {
            roleDao.addRoleDataSource(roleDto.getRoleId(), roleDto.getDataSources());
        }
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
        //先删除之前的
        roleDao.deleRolePermission(roleDto.getRoleId());
        if (!CollectionUtils.isEmpty(roleEntity.getPermissions())) {
            count = roleDao.update(roleEntity);
            if (!CollectionUtils.isEmpty(roleEntity.getPermissions())) {
                Set<Integer> allIds = new HashSet<>();
                List<Integer> ids = roleDto.getPermissions().stream().map(permissionDto -> permissionDto.getPermissionId()).collect(Collectors.toList());
                allIds.addAll(ids);
                while (true) {
                    List<PermissionEntity> permissionEntities = permissionDao.getPermissionByIds(ids);
                    ids.clear();
                    for (PermissionEntity permissionEntity : permissionEntities) {
                        if (permissionEntity.getParentId() != 0) {
                            allIds.add(permissionEntity.getParentId());
                            ids.add(permissionEntity.getParentId());
                        }
                    }
                    if (CollectionUtils.isEmpty(ids)) {
                        break;
                    }
                }
                roleDao.addRolePermissionNew(roleDto.getRoleId(), allIds);
            }
            roleDao.deleRoleDataSource(roleDto.getRoleId());
            if (!CollectionUtils.isEmpty(roleEntity.getDataSources())) {
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
