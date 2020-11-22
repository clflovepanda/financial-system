package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureProjectEntity;
import com.pro.financial.management.dto.ExpenditureProjectDto;
import com.pro.financial.utils.Converter;

public class ExpenditureProjectEntity2Dto implements Converter<ExpenditureProjectEntity, ExpenditureProjectDto> {

    public static final ExpenditureProjectEntity2Dto instance = new ExpenditureProjectEntity2Dto();

    @Override
    public ExpenditureProjectDto convert(ExpenditureProjectEntity expenditureProjectEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureProjectEntity), ExpenditureProjectDto.class);
    }
}
