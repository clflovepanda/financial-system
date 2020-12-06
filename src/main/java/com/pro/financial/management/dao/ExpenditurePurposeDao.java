package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditurePurposeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenditurePurposeDao {

    @Insert("insert into expenditure_purpose (id, expenditure_method_id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.expenditureMethodId}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") ExpenditurePurposeEntity entity);

    @Select("select * from expenditure_purpose order by id asc")
    List<ExpenditurePurposeEntity> getList();
}