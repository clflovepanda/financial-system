package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.InvoiceEntity;
import com.pro.financial.management.dto.InvoiceDto;
import com.pro.financial.utils.Converter;

public class InvoiceEntity2Dto implements Converter<InvoiceEntity, InvoiceDto> {

    public static final InvoiceEntity2Dto instance = new InvoiceEntity2Dto();

    @Override
    public InvoiceDto convert(InvoiceEntity invoiceEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(invoiceEntity), InvoiceDto.class);
    }
}
