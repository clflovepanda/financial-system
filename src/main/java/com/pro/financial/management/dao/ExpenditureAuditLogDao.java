package com.pro.financial.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureAuditLogDao extends BaseMapper<ExpenditureAuditLogEntity> {
//    @Insert("insert into expenditure_audit_log (id, expenditure_id, audit_type, create_user, ctime, state) VALUES " +
//            "(#{entity.id}, #{entity.expenditureId}, #{entity.auditType}, #{entity.createUser}, #{entity.ctime}, #{entity.state})")
//    int insert(@Param("entity") ExpenditureAuditLogEntity entity);

    @Select("select audit_type from expenditure_audit_log where expenditure_id = #{expenditureId} and state = 1 order by ctime desc limit 1")
    String getLastLog(@Param("expenditureId") Integer expenditureId);

    @Select("select * from expenditure_audit_log left join `user` on create_user = user_id where expenditure_id = #{expenditureId} and expenditure_audit_log.state = 1 order by ctime desc")
    List<ExpenditureAuditLogEntity> getLogByEId(Integer expenditureId);
}