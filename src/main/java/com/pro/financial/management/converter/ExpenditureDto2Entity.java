package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.utils.Converter;

public class ExpenditureDto2Entity implements Converter<ExpenditureDto, ExpenditureEntity> {

    public static final ExpenditureDto2Entity instance = new ExpenditureDto2Entity();

    @Override
    public ExpenditureEntity convert(ExpenditureDto expenditureDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureDto), ExpenditureEntity.class);
    }
}
