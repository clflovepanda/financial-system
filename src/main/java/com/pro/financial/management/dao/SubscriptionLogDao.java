package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionLogDao {
    @Insert("insert into subscription_log (`id`, `receivement_id`, `revenue_type_id`, `receivement_money`, `subscription_date`, `state`, `remark`, `create_user`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.revenueTypeId}, #{entity.receivementMoney}, #{entity.subscriptionDate}, #{entity.state}, #{entity.remark}, #{entity.createUser}, #{entity.ctime})")
    int insert(@Param("entity") SubscriptionLogEntity entity);
}