package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.utils.Converter;

public class ProjectDto2Entity implements Converter<ProjectDto, ProjectEntity> {

    public static final ProjectDto2Entity instance = new ProjectDto2Entity();

    @Override
    public ProjectEntity convert(ProjectDto projectDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectDto), ProjectEntity.class);
    }
}
