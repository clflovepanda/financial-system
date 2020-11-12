package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.QuotationEntity;
import com.pro.financial.management.dto.QuotationDto;
import com.pro.financial.utils.Converter;

public class QuotationDto2Entity implements Converter<QuotationDto, QuotationEntity> {

    public static final QuotationDto2Entity instance = new QuotationDto2Entity();
    @Override
    public QuotationEntity convert(QuotationDto QuotationDto) {
        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity = JSONObject.parseObject(JSONObject.toJSONString(QuotationDto), QuotationEntity.class);
        return quotationEntity;
    }
}
