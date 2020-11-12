package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.QuotationEntity;
import com.pro.financial.management.dto.QuotationDto;
import com.pro.financial.utils.Converter;

public class QuotationEntity2Dto implements Converter<QuotationEntity, QuotationDto> {

    public static final QuotationEntity2Dto instance = new QuotationEntity2Dto();
    @Override
    public QuotationDto convert(QuotationEntity QuotationEntity) {
        QuotationDto quotationDto = new QuotationDto();
        quotationDto = JSONObject.parseObject(JSONObject.toJSONString(QuotationEntity), QuotationDto.class);
        return quotationDto;
    }
}
