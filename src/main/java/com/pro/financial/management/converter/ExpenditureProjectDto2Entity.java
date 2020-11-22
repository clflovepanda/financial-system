package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureProjectEntity;
import com.pro.financial.management.dto.ExpenditureProjectDto;
import com.pro.financial.utils.Converter;

public class ExpenditureProjectDto2Entity implements Converter<ExpenditureProjectDto, ExpenditureProjectEntity> {

    public static final ExpenditureProjectDto2Entity instance = new ExpenditureProjectDto2Entity();

    @Override
    public ExpenditureProjectEntity convert(ExpenditureProjectDto expenditureProjectDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureProjectDto), ExpenditureProjectEntity.class);
    }
}
