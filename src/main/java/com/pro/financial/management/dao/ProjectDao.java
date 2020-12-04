package com.pro.financial.management.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.ProjectEntity;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends BaseMapper<ProjectEntity> {
    @Insert("insert into project (`id`, `code`, `name`, `fullname`, `start_date`, `end_date`, `estincome`, `budget`, `description`, `state`, `auditing_state`, `sale_commis_state`, `settlement_state`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
            "(#{entity.id}, #{entity.code}, #{entity.name}, #{entity.fullname}, #{entity.startDate}, #{entity.endDate}, #{entity.estincome}, #{entity.budget}, #{entity.description}, #{entity.state}, #{entity.auditingState}, #{entity.saleCommisState}, #{entity.settlementState}, #{entity.createUser}, #{entity.ctime}, #{entity.update_user}, #{entity.utime})")
    int insert(@Param("entity") ProjectEntity entity);

    @Update("update project set auditing_state = #{auditstate} where id = #{id}")
    int updateAuditState(@Param("id") Integer id, @Param("auditState") Integer auditState);

    @Update("update project set sale_commis_state = #{saleCommisState} where id = #{id}")
    int updateSaleCommisState(@Param("id") Integer id, @Param("saleCommisState") Integer saleCommisState);

    @Update("update project set state = #{state} where id = #{id}")
    int updateState(@Param("id") Integer id, @Param("state") Integer state);

    List<ProjectEntity> getProjectList(ProjectEntity convert);

    int getProjectListCount(ProjectEntity convert);
}