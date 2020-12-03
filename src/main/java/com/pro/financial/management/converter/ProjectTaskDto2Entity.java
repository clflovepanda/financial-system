package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.pro.financial.management.dto.ProjectTaskDto;
import com.pro.financial.utils.Converter;

public class ProjectTaskDto2Entity implements Converter<ProjectTaskDto, ProjectTaskEntity> {

    public static final ProjectTaskDto2Entity instance = new ProjectTaskDto2Entity();

    @Override
    public ProjectTaskEntity convert(ProjectTaskDto projectTaskDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectTaskDto), ProjectTaskEntity.class);
    }
}
