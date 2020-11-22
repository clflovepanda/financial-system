package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureMethodDao {

    @Insert("insert into expenditure_method (id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") ExpenditureMethodEntity entity);
}