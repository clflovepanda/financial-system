package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.InvoiceEntity;
import com.pro.financial.management.dto.InvoiceDto;
import com.pro.financial.utils.Converter;

public class InvoiceDto2Entity implements Converter<InvoiceDto, InvoiceEntity> {

    public static final InvoiceDto2Entity instance = new InvoiceDto2Entity();

    @Override
    public InvoiceEntity convert(InvoiceDto invoiceDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(invoiceDto), InvoiceEntity.class);
    }
}
