package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.utils.Converter;

public class ProjectEntity2Dto implements Converter<ProjectEntity, ProjectDto> {

    public static final ProjectEntity2Dto instance = new ProjectEntity2Dto();

    @Override
    public ProjectDto convert(ProjectEntity entity) {
        return JSONObject.parseObject(JSONObject.toJSONString(entity), ProjectDto.class);
    }
}
