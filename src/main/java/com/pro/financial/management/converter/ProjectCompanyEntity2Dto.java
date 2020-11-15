package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectCompanyEntity;
import com.pro.financial.management.dto.ProjectCompanyDto;
import com.pro.financial.utils.Converter;

public class ProjectCompanyEntity2Dto implements Converter<ProjectCompanyEntity, ProjectCompanyDto> {

    public static final ProjectCompanyEntity2Dto instance = new ProjectCompanyEntity2Dto();

    @Override
    public ProjectCompanyDto convert(ProjectCompanyEntity entity) {
        return JSONObject.parseObject(JSONObject.toJSONString(entity), ProjectCompanyDto.class);
    }
}
