package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dto.RemitterMethodDto;
import com.pro.financial.utils.Converter;

public class RemitterMethodEntity2Dto implements Converter<RemitterMethodEntity, RemitterMethodDto> {

    public static final RemitterMethodEntity2Dto instance = new RemitterMethodEntity2Dto();

    @Override
    public RemitterMethodDto convert(RemitterMethodEntity RemitterMethodEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(RemitterMethodEntity), RemitterMethodDto.class);
    }
}
