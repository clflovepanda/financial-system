package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.Converter;

public class RevenueDto2Entity implements Converter<RevenueDto, RevenueEntity> {

    public static final RevenueDto2Entity instance = new RevenueDto2Entity();

    @Override
    public RevenueEntity convert(RevenueDto RevenueDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(RevenueDto), RevenueEntity.class);
    }
}
