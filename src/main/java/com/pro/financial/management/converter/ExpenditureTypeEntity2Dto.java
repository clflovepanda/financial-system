package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureTypeEntity;
import com.pro.financial.management.dto.ExpenditureTypeDto;
import com.pro.financial.utils.Converter;

public class ExpenditureTypeEntity2Dto implements Converter<ExpenditureTypeEntity, ExpenditureTypeDto> {

    public static final ExpenditureTypeEntity2Dto instance = new ExpenditureTypeEntity2Dto();

    @Override
    public ExpenditureTypeDto convert(ExpenditureTypeEntity expenditureTypeEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureTypeEntity), ExpenditureTypeDto.class);
    }
}
