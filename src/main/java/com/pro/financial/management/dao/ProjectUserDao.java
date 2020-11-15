package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserDao {

    @Insert("insert into project_user (`id`, `project_id`, `user_id`, `type`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.userId}, #{entity.type}, #{entity.ctime})")
    int insert(ProjectUserEntity entity);
}