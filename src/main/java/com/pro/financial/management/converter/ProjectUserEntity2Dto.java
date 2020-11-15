package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectUserEntity;
import com.pro.financial.management.dto.ProjectUserDto;
import com.pro.financial.utils.Converter;

public class ProjectUserEntity2Dto implements Converter<ProjectUserEntity, ProjectUserDto> {

    public static final ProjectUserEntity2Dto instance = new ProjectUserEntity2Dto();

    @Override
    public ProjectUserDto convert(ProjectUserEntity entity) {
        return JSONObject.parseObject(JSONObject.toJSONString(entity), ProjectUserDto.class);
    }
}
