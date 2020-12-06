package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

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
}
