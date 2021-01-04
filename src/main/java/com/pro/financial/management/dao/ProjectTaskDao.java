package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 工时记录表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@Repository
public interface ProjectTaskDao extends BaseMapper<ProjectTaskEntity> {

    List<ProjectTaskEntity> taskList(ProjectTaskEntity projectTaskEntity);

    @Select("<script>" +
            "select * from project_task_relation " +
            "left join project_task using(task_relation_id) " +
            "where project_id in " +
            "<foreach collection='projectIds' item='projectId' index='index' separator=',' open='(' close=')'>" +
            "#{projectId}" +
            "</foreach>" +
            "</script>")
    List<ProjectTaskEntity> getListByProjectIds(List<Integer> projectIds);

    List<ProjectTaskEntity> gettask(@Param("projectId") Integer projectId, @Param("userId") Integer userId, @Param("taskRelationId") Integer taskRelationId);

    @Select("SELECT SUM(amount * take_time) FROM project_task WHERE task_relation_id IN (SELECT task_relation_id FROM project_task_relation WHERE project_id = #{projectId}) AND `status` = 0 AND task_status = '00'")
    BigDecimal getProjectTakeTimeByProjectId(@Param("projectId") Integer projectId);
}
