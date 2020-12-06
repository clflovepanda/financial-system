package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDataSourceDao {

    @Insert("insert into project_data_source (`id`, `project_id`, `data_source_id`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.dataSourceId}, #{entity.ctime})")
    int insert(ProjectDataSourceEntity entity);
}