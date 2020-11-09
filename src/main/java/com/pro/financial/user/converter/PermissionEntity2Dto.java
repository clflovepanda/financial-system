package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.PermissionEntity;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.utils.Converter;

public class PermissionEntity2Dto implements Converter<PermissionEntity, PermissionDto> {

    public static final PermissionEntity2Dto instance = new PermissionEntity2Dto();
    @Override
    public PermissionDto convert(PermissionEntity PermissionEntity) {
        PermissionDto PermissionDto = new PermissionDto();
        PermissionDto = JSONObject.parseObject(JSONObject.toJSONString(PermissionEntity), PermissionDto.class);
        return PermissionDto;
    }
}
