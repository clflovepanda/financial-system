package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ExpenditureProjectEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureProjectDao {

    @Insert("insert into expenditure_project (id, expenditure_id, project_id, ctime) VALUES " +
            "(#{entity.id}, #{entity.expenditureId}, #{entity.projectId}, #{entity.ctime})")
    int insert(@Param("entity") ExpenditureProjectEntity entity);
}