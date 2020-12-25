package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserDao {

    @Insert("insert into project_user (`id`, `project_id`, `user_id`, `type`, `ctime`) VALUES " +
            "(#{entity.id}, #{entity.projectId}, #{entity.userId}, #{entity.type}, #{entity.ctime})")
    int insert(@Param("entity") ProjectUserEntity entity);

    @Insert("<script>" +
            "insert into project_user (`id`, `project_id`, `user_id`, `type`, `ctime`) VALUES " +
            "<foreach collection='entities' item='entity' index='index' separator=','>" +
            "(#{entity.id}, #{entity.projectId}, #{entity.userId}, #{entity.type}, #{entity.ctime})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("entities") List<ProjectUserEntity> entities);

    @Select("<script>" +
            "select * from project_user " +
            "left join user using(user_id) " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<ProjectUserEntity> getPrjectUserList(@Param("projectIds") List<Integer> projectIds);
}