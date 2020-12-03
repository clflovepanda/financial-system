package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureMethodDao {

    @Insert("insert into expenditure_method (id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") ExpenditureMethodEntity entity);

    @Select("select id, name, remark from expenditure_method order by id asc")
    List<ExpenditureMethodEntity> getList();
}