package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dto.RemitterMethodDto;
import com.pro.financial.utils.Converter;

public class RemitterMethodDto2Entity implements Converter<RemitterMethodDto, RemitterMethodEntity> {

    public static final RemitterMethodDto2Entity instance = new RemitterMethodDto2Entity();

    @Override
    public RemitterMethodEntity convert(RemitterMethodDto RemitterMethodDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(RemitterMethodDto), RemitterMethodEntity.class);
    }
}
