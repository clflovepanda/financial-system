package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemitterMethodDao {

    @Insert("insert into remitter_method (id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") RemitterMethodEntity entity);
    @Select("select id, name, remark from remitter_method order by id asc")
    List<RemitterMethodEntity> getList();
}