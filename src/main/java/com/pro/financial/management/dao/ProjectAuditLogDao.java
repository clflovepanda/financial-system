package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectAuditLogEntity;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectAuditLogDao {
    @Insert("insert into project_audit_log (id, project_id, audit_type, create_user, ctime, state) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.auditType}, #{entity.createUser}, #{entity.ctime}, #{entity.state})")
    int insert(@Param("entity") ProjectAuditLogEntity entity);


    @Select("select project_id, audit_type, create_user, project_audit_log.state, project_audit_log.ctime, username createUserName " +
            "from project_audit_log " +
            "left join user " +
            "on user_id = create_user " +
            "where project_id = #{projectId} " +
            "and project_audit_log.state = 1")
    ProjectAuditLogDto getProjectAuditByProjectId(@Param("projectId") Integer projectId);
}