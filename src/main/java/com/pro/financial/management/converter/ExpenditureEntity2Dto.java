package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.utils.Converter;

public class ExpenditureEntity2Dto implements Converter<ExpenditureEntity, ExpenditureDto> {

    public static final ExpenditureEntity2Dto instance = new ExpenditureEntity2Dto();

    @Override
    public ExpenditureDto convert(ExpenditureEntity expenditureEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureEntity), ExpenditureDto.class);
    }
}
