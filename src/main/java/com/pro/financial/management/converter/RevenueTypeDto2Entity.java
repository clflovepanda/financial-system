package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RevenueTypeEntity;
import com.pro.financial.management.dto.RevenueTypeDto;
import com.pro.financial.utils.Converter;

public class RevenueTypeDto2Entity implements Converter<RevenueTypeDto, RevenueTypeEntity> {

    public static final RevenueTypeDto2Entity instance = new RevenueTypeDto2Entity();

    @Override
    public RevenueTypeEntity convert(RevenueTypeDto RevenueTypeDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(RevenueTypeDto), RevenueTypeEntity.class);
    }
}
