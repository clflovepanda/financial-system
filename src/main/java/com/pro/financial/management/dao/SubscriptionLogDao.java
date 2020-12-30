package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SubscriptionLogDao {

//    @Options(useGeneratedKeys = true, keyProperty = "id" , keyColumn = "id")
//    @Insert("insert into subscription_log (`receivement_id`, `revenue_type_id`, project_id, `receivement_money`, `subscription_date`, `state`, `remark`, `create_user`, `ctime`) VALUES " +
//            "(#{entity.receivementId}, #{entity.revenueTypeId}, #{entity.projectId}, #{entity.receivementMoney}, #{entity.subscriptionDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime})")
    int insert(SubscriptionLogEntity entity);

    @Select("<script> " +
            "select * from subscription_log " +
            "where receivement_id " +
            "in <foreach item='item' index='index' collection='receivementIds' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<SubscriptionLogEntity> getListByReceivementIds(@Param("receivementIds") List<Integer> receivementIds);

    List<SubscriptionLogEntity> getListByReceivementIdsnew(@Param("receivementIds") List<Integer> receivementIds, @Param("projectName") String projectName,
                                                           @Param("dataSourceId") String dataSourceId, @Param("revenueTypeId")  String revenueTypeId);

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

    @Update("update subscription_log set state = 0 where receivement_id = #{id}")
    int deleteByReceivementId(@Param("id") Integer id);

    @Select("SELECT (SELECT receivement_money FROM receivement where id = #{id}) - (SELECT sum(receivement_money) FROM subscription_log where receivement_id = #{id} AND state = 1)")
    BigDecimal gethadSubscriptionTotalMoneyByRId(@Param("id") Integer id);
}