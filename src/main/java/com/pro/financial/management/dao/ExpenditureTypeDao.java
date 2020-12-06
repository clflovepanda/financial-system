package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureTypeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditureTypeDao {

    @Insert("insert into expenditure_type (id, expenditure_method_id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.expenditureMethodId}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") ExpenditureTypeEntity entity);

    @Select("select * from expenditure_type order by id asc")
    List<ExpenditureTypeEntity> getList();
}