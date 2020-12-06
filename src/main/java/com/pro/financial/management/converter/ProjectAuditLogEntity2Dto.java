package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.ProjectAuditLogEntity;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import com.pro.financial.utils.Converter;

public class ProjectAuditLogEntity2Dto implements Converter<ProjectAuditLogEntity, ProjectAuditLogDto> {

    public static final ProjectAuditLogEntity2Dto instance = new ProjectAuditLogEntity2Dto();

    @Override
    public ProjectAuditLogDto convert(ProjectAuditLogEntity projectAuditLogEntity) {
        return JSONObject.parseObject(JSONObject.toJSONString(projectAuditLogEntity), ProjectAuditLogDto.class);
    }
}
