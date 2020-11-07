package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dao.entity.UserEntity;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.Converter;

public class RoleEntity2Dto implements Converter<RoleEntity, RoleDto> {

    public static final RoleEntity2Dto instance = new RoleEntity2Dto();
    @Override
    public RoleDto convert(RoleEntity roleEntity) {
        RoleDto roleDto = new RoleDto();
        roleDto = JSONObject.parseObject(JSONObject.toJSONString(roleEntity), RoleDto.class);
        return roleDto;
    }
}
