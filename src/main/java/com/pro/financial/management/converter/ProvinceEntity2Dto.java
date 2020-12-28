package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProvinceEntity;
import com.pro.financial.management.dto.ProvinceDto;
import com.pro.financial.utils.Converter;

public class ProvinceEntity2Dto implements Converter<ProvinceEntity, ProvinceDto> {

    public static final ProvinceEntity2Dto instance = new ProvinceEntity2Dto();

    @Override
    public ProvinceDto convert(ProvinceEntity provinceEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(provinceEntity), ProvinceDto.class);
    }
}
