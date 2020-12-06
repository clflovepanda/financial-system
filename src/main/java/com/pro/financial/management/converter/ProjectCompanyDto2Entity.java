package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectCompanyEntity;
import com.pro.financial.management.dto.ProjectCompanyDto;
import com.pro.financial.utils.Converter;

public class ProjectCompanyDto2Entity implements Converter<ProjectCompanyDto, ProjectCompanyEntity> {

    public static final ProjectCompanyDto2Entity instance = new ProjectCompanyDto2Entity();

    @Override
    public ProjectCompanyEntity convert(ProjectCompanyDto projectCompanyDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectCompanyDto), ProjectCompanyEntity.class);
    }
}
