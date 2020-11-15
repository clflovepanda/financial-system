package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import com.pro.financial.utils.Converter;

public class ProjectDataSourceDto2Entity implements Converter<ProjectDataSourceDto, ProjectDataSourceEntity> {

    public static final ProjectDataSourceDto2Entity instance = new ProjectDataSourceDto2Entity();

    @Override
    public ProjectDataSourceEntity convert(ProjectDataSourceDto projectDataSourceDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectDataSourceDto), ProjectDataSourceEntity.class);
    }
}
