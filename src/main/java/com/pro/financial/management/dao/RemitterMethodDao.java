package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RemitterMethodDao {

    @Insert("insert into remitter_method (id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") RemitterMethodEntity entity);
}