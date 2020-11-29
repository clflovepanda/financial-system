package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectAuditLogEntity;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import com.pro.financial.utils.Converter;

public class ProjectAuditLogDto2Entity implements Converter<ProjectAuditLogDto, ProjectAuditLogEntity> {

    public static final ProjectAuditLogDto2Entity instance = new ProjectAuditLogDto2Entity();

    @Override
    public ProjectAuditLogEntity convert(ProjectAuditLogDto projectAuditLogDto) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectAuditLogDto), ProjectAuditLogEntity.class);
    }
}
