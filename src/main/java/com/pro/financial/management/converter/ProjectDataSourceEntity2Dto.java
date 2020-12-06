package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import com.pro.financial.utils.Converter;

public class ProjectDataSourceEntity2Dto implements Converter<ProjectDataSourceEntity, ProjectDataSourceDto> {

    public static final ProjectDataSourceEntity2Dto instance = new ProjectDataSourceEntity2Dto();

    @Override
    public ProjectDataSourceDto convert(ProjectDataSourceEntity entity) {
        return JSONObject.parseObject(JSONObject.toJSONString(entity), ProjectDataSourceDto.class);
    }
}
