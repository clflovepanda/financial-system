package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ProjectAuditLogDto2Entity;
import com.pro.financial.management.dao.ProjectAuditLogDao;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectAuditLogBiz {
    @Autowired
    private ProjectAuditLogDao projectAuditLogDao;

    public int addProjectAuditLog(ProjectAuditLogDto projectAuditLogDto) {
        return projectAuditLogDao.insert(ProjectAuditLogDto2Entity.instance.convert(projectAuditLogDto));
    }

    public ProjectAuditLogDto getProjectAuditByProjectId(Integer projectId) {
        return projectAuditLogDao.getProjectAuditByProjectId(projectId);
    }

    public int removeLog(Integer projectId) {
        return projectAuditLogDao.removeLog(projectId);
    }
}
