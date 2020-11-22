package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.utils.Converter;

public class ReceivementDto2Entity implements Converter<ReceivementDto, ReceivementEntity> {

    public static final ReceivementDto2Entity instance = new ReceivementDto2Entity();

    @Override
    public ReceivementEntity convert(ReceivementDto ReceivementDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(ReceivementDto), ReceivementEntity.class);
    }
}
