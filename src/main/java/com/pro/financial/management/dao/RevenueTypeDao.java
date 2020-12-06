package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.RevenueTypeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueTypeDao {

    @Insert("insert into revenue_type (`id`, `name`, `remark`) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") RevenueTypeEntity entity);
}