package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionLogDao {
    @Insert("insert into subscription_log (`id`, `receivement_id`, `revenue_type_id`, project_id, `receivement_money`, `subscription_date`, `state`, `remark`, `create_user`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.revenueTypeId}, #{entity.projectId}, #{entity.receivementMoney}, #{entity.subscriptionDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime})")
    int insert(@Param("entity") SubscriptionLogEntity entity);

    @Select("<script> " +
            "select * from subscription_log where receivement_id " +
            "in <foreach item='item' index='index' collection='receivementIds' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<SubscriptionLogEntity> getListByReceivementIds(@Param("receivementIds") List<Integer> receivementIds);

    @Select("<script> " +
            "select * from subscription_log where project_id " +
            "in <foreach item='item' index='index' collection='projectIds' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<SubscriptionLogEntity> getListByProjectIds(@Param("projectIds") List<Integer> projectIds);

    @Select("SELECT subscription_log.*, p.name projectName, u.username ,ds.data_source_name, rt.revenue_type_name FROM subscription_log " +
            "LEFT JOIN project p USING (project_id) " +
            "LEFT JOIN project_data_source USING (project_id) " +
            "LEFT JOIN data_source ds USING (data_source_id) " +
            "LEFT JOIN revenue_type rt USING (revenue_type_id) " +
            "LEFT JOIN `user` u ON subscription_log.create_user = user_id " +
            "where receivement_id = #{receivementId} and subscription_log.state = 1")
    List<SubscriptionLogEntity> getListByProjectId(@Param("receivementId") Integer receivementId);
}