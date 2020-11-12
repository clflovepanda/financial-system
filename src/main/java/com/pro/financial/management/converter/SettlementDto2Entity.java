package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.SettlementEntity;
import com.pro.financial.management.dto.SettlementDto;
import com.pro.financial.utils.Converter;

public class SettlementDto2Entity implements Converter<SettlementDto, SettlementEntity> {

    public static final SettlementDto2Entity instance = new SettlementDto2Entity();
    @Override
    public SettlementEntity convert(SettlementDto SettlementDto) {
        SettlementEntity settlementEntity = new SettlementEntity();
        settlementEntity = JSONObject.parseObject(JSONObject.toJSONString(SettlementDto), SettlementEntity.class);
        return settlementEntity;
    }
}
