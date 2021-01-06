package com.pro.financial.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.RevenueEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RevenueDao extends BaseMapper<RevenueEntity> {

//    @Insert("insert into revenue ( `receivement_id`, `subscription_log_id`, `revenue_no`, `revenue_type_id`, `data_source_id`, `project_id`, `cny_money`, `remark`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
//            "(#{entity.receivementId},#{entity.subscriptionLogId} ,#{entity.revenueNo}, #{entity.revenueTypeId}, #{entity.dataSourceId}, #{entity.projectId}, #{entity.cnyMoney}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime})")
//    int insert(@Param("entity") RevenueEntity entity);

    @Select("<script>" +
            "select * from revenue " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<RevenueEntity> getRevenueList(@Param("projectIds") List<Integer> projectIds);

    List<RevenueEntity> searchList(@Param("projectId") String projectId, @Param("revenueNo") String revenueNo,
                                   @Param("remitterMethodId") String remitterMethodId, @Param("receivementTypeId") String receivementTypeId,
                                   @Param("companyId") String companyId, @Param("remitter") String remitter,
                                   @Param("createUser") String createUser, @Param("startDt") Date startDate, @Param("endDt") Date endDate,
                                   @Param("projectName") String projectName, @Param("projectNo") String projectNo, @Param("revenueTypeId") String revenueTypeId,
                                   @Param("limit") Integer limit, @Param("offset") Integer offset);
    @Select("SELECT revenue_no FROM revenue ORDER BY id DESC LIMIT 0,1")
    String selectLastNo();

    @Update("update revenue set `delete` = 0 where receivement_id = #{id}")
    int deleteByReceivementId(@Param("id") Integer id);

    int getCount(@Param("projectId") String projectId, @Param("revenueNo") String revenueNo,
                 @Param("remitterMethodId") String remitterMethodId, @Param("receivementTypeId") String receivementTypeId,
                 @Param("companyId") String companyId, @Param("remitter") String remitter,
                 @Param("createUser") String createUser, @Param("startDt") Date startDate, @Param("endDt") Date endDate,
                 @Param("projectName") String projectName, @Param("projectNo") String projectNo, @Param("revenueTypeId") String revenueTypeId);
}