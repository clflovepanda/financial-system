package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.utils.Converter;

public class ReceivementEntity2Dto implements Converter<ReceivementEntity, ReceivementDto> {

    public static final ReceivementEntity2Dto instance = new ReceivementEntity2Dto();

    @Override
    public ReceivementDto convert(ReceivementEntity ReceivementEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(ReceivementEntity), ReceivementDto.class);
    }
}
