package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.RevenueEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueDao {

    @Insert("insert into revenue (`id`, `receivement_id`, `revenue_type_id`, `data_source_id`, `cny_money`, `delete`, `remark`, `create_user`, `ctime`, `update_user`, `utime`) VALUES " +
            "(#{entity.id}, #{entity.receivementId}, #{entity.revenueTypeId}, #{entity.dataSourceId}, #{entity.cnyMoney}, #{entity.delete}, #{entity.remark}, #{entity.createUser}, #{entity.ctime}, #{entity.updateUser}, #{entity.utime}, #{entity.}, #{entity.}, #{entity.}, #{entity.}, #{entity.}, )")
    int insert(@Param("entity") RevenueEntity entity);
}