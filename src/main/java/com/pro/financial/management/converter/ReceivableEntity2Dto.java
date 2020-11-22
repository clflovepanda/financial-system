package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivableEntity;
import com.pro.financial.management.dto.ReceivableDto;
import com.pro.financial.utils.Converter;

public class ReceivableEntity2Dto implements Converter<ReceivableEntity, ReceivableDto> {
    public static final ReceivableEntity2Dto instance = new ReceivableEntity2Dto();
    @Override
    public ReceivableDto convert(ReceivableEntity ReceivableEntity) {
        ReceivableDto receivableDto = new ReceivableDto();
        receivableDto = JSONObject.parseObject(JSONObject.toJSONString(ReceivableEntity), ReceivableDto.class);
        return receivableDto;
    }
}
