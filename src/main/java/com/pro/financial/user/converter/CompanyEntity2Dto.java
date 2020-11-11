package com.pro.financial.user.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.dao.entity.CompanyEntity;
import com.pro.financial.user.dao.entity.RoleEntity;
import com.pro.financial.user.dto.CompanyDto;
import com.pro.financial.user.dto.RoleDto;
import com.pro.financial.utils.Converter;

public class CompanyEntity2Dto implements Converter<CompanyEntity, CompanyDto> {

    public static final CompanyEntity2Dto instance = new CompanyEntity2Dto();
    @Override
    public CompanyDto convert(CompanyEntity companyEntity) {
        CompanyDto companyDto = new CompanyDto();
        companyDto = JSONObject.parseObject(JSONObject.toJSONString(companyEntity), CompanyDto.class);
        return companyDto;
    }
}
