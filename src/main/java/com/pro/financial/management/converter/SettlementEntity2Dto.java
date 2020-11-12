package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.SettlementEntity;
import com.pro.financial.management.dto.SettlementDto;
import com.pro.financial.utils.Converter;

public class SettlementEntity2Dto implements Converter<SettlementEntity, SettlementDto> {

    public static final SettlementEntity2Dto instance = new SettlementEntity2Dto();
    @Override
    public SettlementDto convert(SettlementEntity SettlementEntity) {
        SettlementDto settlementDto = new SettlementDto();
        settlementDto = JSONObject.parseObject(JSONObject.toJSONString(SettlementEntity), SettlementDto.class);
        return settlementDto;
    }
}
