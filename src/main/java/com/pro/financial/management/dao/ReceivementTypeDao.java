package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivementTypeDao {

    @Insert("insert into receivement_type (id, name, remark) VALUES " +
            "(#{entity.id}, #{entity.name}, #{entity.remark})")
    int insert(@Param("entity") ReceivementTypeEntity entity);

    @Select("select * from receivement_type order by id asc")
    List<ReceivementTypeEntity> getList();
}