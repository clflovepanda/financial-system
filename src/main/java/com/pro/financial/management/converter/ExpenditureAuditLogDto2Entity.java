package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import com.pro.financial.utils.Converter;

public class ExpenditureAuditLogDto2Entity implements Converter<ExpenditureAuditLogDto, ExpenditureAuditLogEntity> {

    public static final ExpenditureAuditLogDto2Entity instance = new ExpenditureAuditLogDto2Entity();

    @Override
    public ExpenditureAuditLogEntity convert(ExpenditureAuditLogDto expenditureAuditLogDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(expenditureAuditLogDto), ExpenditureAuditLogEntity.class);
    }
}
