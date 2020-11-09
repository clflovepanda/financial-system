package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.utils.Converter;

public class RoleDto2Entity implements Converter<RoleDto, RoleEntity> {

    public static final RoleDto2Entity instance = new RoleDto2Entity();
    @Override
    public RoleEntity convert(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity = JSONObject.parseObject(JSONObject.toJSONString(roleDto), RoleEntity.class);
        return roleEntity;
    }
}
