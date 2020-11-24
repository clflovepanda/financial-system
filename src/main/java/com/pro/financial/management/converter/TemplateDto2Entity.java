package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.TemplateEntity;
import com.pro.financial.management.dto.TemplateDto;
import com.pro.financial.utils.Converter;

public class TemplateDto2Entity implements Converter<TemplateDto, TemplateEntity> {

    public static final TemplateDto2Entity instance = new TemplateDto2Entity();

    @Override
    public TemplateEntity convert(TemplateDto templateDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(templateDto), TemplateEntity.class);
    }
}
