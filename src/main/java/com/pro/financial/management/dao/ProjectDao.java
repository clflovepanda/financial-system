package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectEntity;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao {
    @Insert("insert into project (`id`, `code`, `name`, `fullname`, `start_date`, `end_date`, `estincome`, `budget`, `description`, `state`, `auditing_state`, `sale_commis_state`, `settlement_state`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
            "(#{entity.id}, #{entity.code}, #{entity.name}, #{entity.fullname}, #{entity.startDate}, #{entity.endDate}, #{entity.estincome}, #{entity.budget}, #{entity.description}, #{entity.state}, #{entity.auditingState}, #{entity.saleCommisState}, #{entity.settlementState}, #{entity.createUser}, #{entity.ctime}, #{entity.update_user}, #{entity.utime})")
    int insert(@Param("entity") ProjectEntity entity);
}