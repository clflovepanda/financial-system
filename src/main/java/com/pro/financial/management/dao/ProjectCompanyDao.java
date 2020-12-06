package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectCompanyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCompanyDao {

    @Insert("insert into project_company (`id`, `project_id`, `company_id`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.companyId}, #{entity.ctime})")
    int insert(@Param("entity") ProjectCompanyEntity entity);
}