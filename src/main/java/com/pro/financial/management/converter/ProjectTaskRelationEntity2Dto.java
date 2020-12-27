package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectTaskRelationEntity;
import com.pro.financial.management.dto.ProjectTaskRelationDto;
import com.pro.financial.utils.Converter;

public class ProjectTaskRelationEntity2Dto implements Converter<ProjectTaskRelationEntity, ProjectTaskRelationDto> {
    public static final ProjectTaskRelationEntity2Dto instance = new ProjectTaskRelationEntity2Dto();
    @Override
    public ProjectTaskRelationDto convert(ProjectTaskRelationEntity projectTaskRelationEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectTaskRelationEntity), ProjectTaskRelationDto.class);
    }
}
