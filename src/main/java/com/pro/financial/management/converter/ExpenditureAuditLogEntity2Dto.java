package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import com.pro.financial.utils.Converter;

public class ExpenditureAuditLogEntity2Dto implements Converter<ExpenditureAuditLogEntity, ExpenditureAuditLogDto> {

    public static final ExpenditureAuditLogEntity2Dto instance = new ExpenditureAuditLogEntity2Dto();

    @Override
    public ExpenditureAuditLogDto convert(ExpenditureAuditLogEntity expenditureAuditLogEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureAuditLogEntity), ExpenditureAuditLogDto.class);
    }
}
