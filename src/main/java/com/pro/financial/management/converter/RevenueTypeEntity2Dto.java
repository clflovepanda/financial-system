package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RevenueTypeEntity;
import com.pro.financial.management.dto.RevenueTypeDto;
import com.pro.financial.utils.Converter;

public class RevenueTypeEntity2Dto implements Converter<RevenueTypeEntity, RevenueTypeDto> {

    public static final RevenueTypeEntity2Dto instance = new RevenueTypeEntity2Dto();

    @Override
    public RevenueTypeDto convert(RevenueTypeEntity RevenueTypeEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(RevenueTypeEntity), RevenueTypeDto.class);
    }
}
