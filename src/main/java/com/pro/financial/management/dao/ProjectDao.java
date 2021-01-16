package com.pro.financial.management.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.ProjectEntity;

import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.user.dto.DataSourceDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectDao extends BaseMapper<ProjectEntity> {

//    @Options(useGeneratedKeys = true, keyProperty = "project_id", keyColumn = "projectId")
//    @Insert("insert into project (`project_id`, `code`, `name`, `fullname`, `start_date`, `end_date`, `estincome`, `budget`, `description`, `state`, `auditing_state`, `sale_commis_state`, `settlement_state`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
//            "(#{entity.id}, #{entity.code}, #{entity.name}, #{entity.fullname}, #{entity.startDate}, #{entity.endDate}, #{entity.estincome}, #{entity.budget}, #{entity.description}, #{entity.state}, #{entity.auditingState}, #{entity.saleCommisState}, #{entity.settlementState}, #{entity.createUser}, #{entity.ctime}, #{entity.update_user}, #{entity.utime})")
//    int insert(@Param("entity") ProjectEntity entity);

    @Update("update project set auditing_state = #{auditState} where project_id = #{id}")
    int updateAuditState(@Param("id") Integer id, @Param("auditState") Integer auditState);

    @Update("update project set sale_commis_state = #{saleCommisState} where project_id = #{id}")
    int updateSaleCommisState(@Param("id") Integer id, @Param("saleCommisState") Integer saleCommisState);

    @Update("update project set state = #{state} where project_id = #{id}")
    int updateState(@Param("id") Integer id, @Param("state") Integer state);

    @Select("<script>" +
            "select * from project " +
            "left join project_data_source " +
            "using(project_id) " +
            "left join data_source " +
            "using(data_source_id) " +
            "where project_id in " +
            "<foreach collection='ids' item='id' index='index' separator=',' open='(' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<ProjectEntity> getProjectList(@Param("ids") List<Integer> ids);

    @Select("select * from project " +
            "left join project_data_source " +
            "using(project_id) " +
            "left join data_source " +
            "using(data_source_id) where project.auditing_state = 1 limit 0,10")
    List<ProjectEntity> getAllProjectList();

    List<ProjectEntity> getList(@Param("ids") List<Integer> projectIds, @Param("projectNo") String projectNo, @Param("projectName") String projectName,
                                @Param("managerName") String managerName, @Param("salesName") String salesName,
                                @Param("userNames") String userNames, @Param("settlementState") String settlementState,
                                @Param("state") String state, @Param("saleCommisState") String saleCommisState,
                                @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("auditingState") String auditingState,
                                @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Select("select * from project " +
            "left join project_data_source " +
            "using(project_id) " +
            "left join data_source " +
            "using(data_source_id) " +
            "where project_id = #{projectId}" )
    ProjectEntity getProjectById(@Param("projectId") int projectId);

    @Select("select * from project " +
            "left join project_data_source " +
            "using(project_id) " +
            "left join data_source " +
            "using(data_source_id) " +
            "where name like #{keyWords} and project.auditing_state = 1" )
    List<ProjectEntity> getProjectByKeywords(@Param("keyWords") String keyWords);

    @Select("SELECT code FROM project ORDER BY project_id DESC LIMIT 0,1")
    String selectLastNo();

    List<ProjectEntity> statistics(@Param("dataSourceId") String dataSourceId, @Param("keyWord") String keyWord,
                                   @Param("startDt") Date startDate, @Param("endDt") Date endDate, @Param("state") String state,
                                   @Param("limit") Integer limit, @Param("offset") Integer offset);


    int getCount(@Param("ids") List<Integer> projectIds, @Param("projectNo") String projectNo, @Param("projectName") String projectName,
                 @Param("managerName") String managerName, @Param("salesName") String salesName,
                 @Param("userNames") String userNames, @Param("settlementState") String settlementState,
                 @Param("state") String state, @Param("saleCommisState") String saleCommisState,
                 @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("auditingState") String auditingState);

    int statisticsCount(@Param("dataSourceId") String dataSourceId, @Param("keyWord") String keyWord,
                        @Param("startDt") Date startDate, @Param("endDt") Date endDate, @Param("state") String state);
}