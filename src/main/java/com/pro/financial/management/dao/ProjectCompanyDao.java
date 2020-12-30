package com.pro.financial.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.ProjectCompanyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCompanyDao extends BaseMapper<ProjectCompanyEntity> {

    @Insert("insert into project_company (`id`, `project_id`, `company_id`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.companyId}, #{entity.ctime})")
    int insert(@Param("entity") ProjectCompanyEntity entity);

    @Select("select co_name from company " +
            "left join project_company " +
            "using(company_id) where project_id = #{id}")
    String getCompanyByProjectId(@Param("id") Integer id);
}