package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.Converter;

public class RevenueEntity2Dto implements Converter<RevenueEntity, RevenueDto> {

    public static final RevenueEntity2Dto instance = new RevenueEntity2Dto();

    @Override
    public RevenueDto convert(RevenueEntity RevenueEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(RevenueEntity), RevenueDto.class);
    }
}
