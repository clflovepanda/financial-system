package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.ProjectTaskRelationDao;
import com.pro.financial.management.dao.entity.ProjectTaskRelationEntity;
import com.pro.financial.management.dto.ProjectTaskRelationDto;
import com.pro.financial.utils.Converter;

public class ProjectTaskRelationDto2Entity implements Converter<ProjectTaskRelationDto, ProjectTaskRelationEntity> {

    public static final ProjectTaskRelationDto2Entity instance = new ProjectTaskRelationDto2Entity();

    @Override
    public ProjectTaskRelationEntity convert(ProjectTaskRelationDto projectTaskRelationDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectTaskRelationDto), ProjectTaskRelationEntity.class);
    }
}
