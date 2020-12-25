package com.pro.financial.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDataSourceDao extends BaseMapper<ProjectDataSourceEntity> {

//    @Insert("insert into project_data_source (`id`, `project_id`, `data_source_id`, `ctime`) VALUES " +
//            "(#{entity.id}, #{entity.projectId}, #{entity.dataSourceId}, #{entity.ctime})")
//    int insert(@Param("entity") ProjectDataSourceEntity entity);
}