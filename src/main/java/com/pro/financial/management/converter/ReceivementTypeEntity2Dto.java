package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dto.ReceivementTypeDto;
import com.pro.financial.utils.Converter;

public class ReceivementTypeEntity2Dto implements Converter<ReceivementTypeEntity, ReceivementTypeDto> {

    public static final ReceivementTypeEntity2Dto instance = new ReceivementTypeEntity2Dto();

    @Override
    public ReceivementTypeDto convert(ReceivementTypeEntity ReceivementTypeEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(ReceivementTypeEntity), ReceivementTypeDto.class);
    }
}
