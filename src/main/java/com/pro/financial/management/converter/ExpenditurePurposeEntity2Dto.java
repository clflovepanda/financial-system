package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditurePurposeEntity;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import com.pro.financial.utils.Converter;

public class ExpenditurePurposeEntity2Dto implements Converter<ExpenditurePurposeEntity, ExpenditurePurposeDto> {

    public static final ExpenditurePurposeEntity2Dto instance = new ExpenditurePurposeEntity2Dto();

    @Override
    public ExpenditurePurposeDto convert(ExpenditurePurposeEntity expenditurePurposeEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditurePurposeEntity), ExpenditurePurposeDto.class);
    }
}
