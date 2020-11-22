package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dto.ExpenditureMethodDto;
import com.pro.financial.utils.Converter;

public class ExpenditureMethodDto2Entity implements Converter<ExpenditureMethodDto, ExpenditureMethodEntity> {

    public static final ExpenditureMethodDto2Entity instance = new ExpenditureMethodDto2Entity();

    @Override
    public ExpenditureMethodEntity convert(ExpenditureMethodDto expenditureMethodDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureMethodDto), ExpenditureMethodEntity.class);
    }
}
