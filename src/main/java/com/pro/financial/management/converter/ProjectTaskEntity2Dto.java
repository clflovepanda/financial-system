package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.pro.financial.management.dto.ProjectTaskDto;
import com.pro.financial.utils.Converter;

public class ProjectTaskEntity2Dto implements Converter<ProjectTaskEntity, ProjectTaskDto> {

    public static final ProjectTaskEntity2Dto instance = new ProjectTaskEntity2Dto();

    @Override
    public ProjectTaskDto convert(ProjectTaskEntity projectTaskEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectTaskEntity), ProjectTaskDto.class);
    }
}
