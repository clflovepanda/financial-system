package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.TemplateEntity;
import com.pro.financial.management.dto.TemplateDto;
import com.pro.financial.utils.Converter;

public class TemplateEntity2Dto implements Converter<TemplateEntity, TemplateDto> {

    public static final TemplateEntity2Dto instance = new TemplateEntity2Dto();

    @Override
    public TemplateDto convert(TemplateEntity templateEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(templateEntity), TemplateDto.class);
    }
}
