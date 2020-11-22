package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dto.ExpenditureMethodDto;
import com.pro.financial.utils.Converter;

public class ExpenditureMethodEntity2Dto implements Converter<ExpenditureMethodEntity, ExpenditureMethodDto> {

    public static final ExpenditureMethodEntity2Dto instance = new ExpenditureMethodEntity2Dto();

    @Override
    public ExpenditureMethodDto convert(ExpenditureMethodEntity expenditureMethodEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureMethodEntity), ExpenditureMethodDto.class);
    }
}
