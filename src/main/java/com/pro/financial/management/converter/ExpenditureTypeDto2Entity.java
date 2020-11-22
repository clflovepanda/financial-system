package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureTypeEntity;
import com.pro.financial.management.dto.ExpenditureTypeDto;
import com.pro.financial.utils.Converter;

public class ExpenditureTypeDto2Entity implements Converter<ExpenditureTypeDto, ExpenditureTypeEntity> {

    public static final ExpenditureTypeDto2Entity instance = new ExpenditureTypeDto2Entity();

    @Override
    public ExpenditureTypeEntity convert(ExpenditureTypeDto expenditureTypeDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureTypeDto), ExpenditureTypeEntity.class);
    }
}
