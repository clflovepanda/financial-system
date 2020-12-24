package com.pro.financial.user.biz;

import com.pro.financial.user.converter.PermissionEntity2Dto;
import com.pro.financial.user.dao.PermissionDao;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionBiz {

    @Autowired
    private PermissionDao permissionDao;

    public List<PermissionDto> getPermissionList(String keyWords) {
        List<PermissionEntity> permissionEntities = permissionDao.selectPermission(keyWords);
        return ConvertUtil.convert(PermissionEntity2Dto.instance, permissionEntities);
    }

    public List<PermissionDto> getFirstPermissionList() {
        List<PermissionEntity> permissionEntities = permissionDao.selectFristPermission();
        return ConvertUtil.convert(PermissionEntity2Dto.instance, permissionEntities);
    }
    public List<PermissionDto> getOtherPermissionList() {
        List<PermissionEntity> permissionEntities = permissionDao.selectOtherPermission();
        return ConvertUtil.convert(PermissionEntity2Dto.instance, permissionEntities);
    }
}
