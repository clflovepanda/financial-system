package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectUserEntity;
import com.pro.financial.management.dto.ProjectUserDto;
import com.pro.financial.utils.Converter;

public class ProjectUserDto2Entity implements Converter<ProjectUserDto, ProjectUserEntity> {

    public static final ProjectUserDto2Entity instance = new ProjectUserDto2Entity();

    @Override
    public ProjectUserEntity convert(ProjectUserDto projectUserDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectUserDto), ProjectUserEntity.class);
    }
}
