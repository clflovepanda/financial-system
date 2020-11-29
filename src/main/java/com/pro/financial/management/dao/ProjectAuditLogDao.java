package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectAuditLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAuditLogDao {
    @Insert("insert into project_audit_log (id, project_id, audit_type, create_user, ctime, state) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.auditType}, #{entity.createUser}, #{entity.ctime}, #{entity.state})")
    int insert(@Param("entity") ProjectAuditLogEntity entity);
}