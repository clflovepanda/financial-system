package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionLogDao {
    @Insert("insert into subscription_log (`id`, `receivement_id`, `revenue_type_id`, `receivement_money`, `subscription_date`, `state`, `remark`, `create_user`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.revenueTypeId}, #{entity.receivementMoney}, #{entity.subscriptionDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime})")
    int insert(@Param("entity") SubscriptionLogEntity entity);

    @Select("<script> " +
            "select * from subscription_log where receivement_id " +
            "in <foreach item='item' index='index' collection='receivementIds' open='(' separator=',' close=')'> #{item} </foreach> " +
            "</script>")
    List<SubscriptionLogEntity> getListByReceivementIds(@Param("receivementIds") List<Integer> receivementIds);
}