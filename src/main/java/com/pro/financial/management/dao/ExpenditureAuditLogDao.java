package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureAuditLogDao {
    @Insert("insert into expenditure_audit_log (id, expenditure_id, audit_type, create_user, ctime, state) VALUES " +
            "(#{entity.id}, #{entity.expenditureId}, #{entity.auditType}, #{entity.createUser}, #{entity.ctime}, #{entity.state})")
    int insert(@Param("entity") ExpenditureAuditLogEntity entity);

    @Select("select audit_type from expenditure_audit_log where expenditure_id = #{expenditureId} and state = 1 order by ctime desc limit 1")
    String getLastLog(@Param("expenditureId") Integer expenditureId);
}