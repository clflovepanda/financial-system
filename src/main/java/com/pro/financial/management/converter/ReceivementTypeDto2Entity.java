package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dto.ReceivementTypeDto;
import com.pro.financial.utils.Converter;

public class ReceivementTypeDto2Entity implements Converter<ReceivementTypeDto, ReceivementTypeEntity> {

    public static final ReceivementTypeDto2Entity instance = new ReceivementTypeDto2Entity();

    @Override
    public ReceivementTypeEntity convert(ReceivementTypeDto ReceivementTypeDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(ReceivementTypeDto), ReceivementTypeEntity.class);
    }
}
