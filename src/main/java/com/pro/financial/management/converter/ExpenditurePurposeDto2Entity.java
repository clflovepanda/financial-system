package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditurePurposeEntity;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import com.pro.financial.utils.Converter;

public class ExpenditurePurposeDto2Entity implements Converter<ExpenditurePurposeDto, ExpenditurePurposeEntity> {

    public static final ExpenditurePurposeDto2Entity instance = new ExpenditurePurposeDto2Entity();

    @Override
    public ExpenditurePurposeEntity convert(ExpenditurePurposeDto expenditurePurposeDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditurePurposeDto), ExpenditurePurposeEntity.class);
    }
}
