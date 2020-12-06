package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivableEntity;
import com.pro.financial.management.dto.ReceivableDto;
import com.pro.financial.utils.Converter;

public class ReceivableDto2Entity implements Converter<ReceivableDto, ReceivableEntity> {

    public static final ReceivableDto2Entity instance = new ReceivableDto2Entity();

    @Override
    public ReceivableEntity convert(ReceivableDto receivableDto) {
        ReceivableEntity receivableEntity = new ReceivableEntity();
        receivableEntity = JSONObject.parseObject(JSONObject.toJSONString(receivableDto), ReceivableEntity.class);
        return receivableEntity;
    }
}
