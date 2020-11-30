package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.ProjectTaskRelationDto2Entity;
import com.pro.financial.management.dao.ProjectTaskDao;
import com.pro.financial.management.dao.ProjectTaskRelationDao;
import com.pro.financial.management.dao.entity.ProjectTaskEntity;
import com.pro.financial.management.dto.ProjectTaskRelationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工时记录表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@Service
public class ProjectTaskBiz extends ServiceImpl<ProjectTaskDao, ProjectTaskEntity> {

    @Autowired
    private ProjectTaskRelationDao projectTaskRelationDao;

    /**
     * 添加/修改项目对应工时模板,项目可以对应多套模板
     * 如果传入主键id则更新,未传入则添加
     * @param jsonInfo
     * @return
     */
    public JSONObject addRelation(JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        if (jsonInfo.getInteger("projectId") == null || jsonInfo.getInteger("projectId") < 1) {
            result.put("code", 1001);
            result.put("msg", "未传入项目id");
            return result;
        }
        if (jsonInfo.getInteger("templateFlag") == null || jsonInfo.getInteger("templateFlag") < 1) {
            result.put("code", 1001);
            result.put("msg", "未传入模板id");
            return result;
        }
        ProjectTaskRelationDto projectTaskRelationDto = JSONObject.parseObject(jsonInfo.toJSONString(), ProjectTaskRelationDto.class);
        if (projectTaskRelationDto.getTaskRelationId() != null) {
            projectTaskRelationDao.updateById(ProjectTaskRelationDto2Entity.instance.convert(projectTaskRelationDto));
        } else {
            projectTaskRelationDao.insert(ProjectTaskRelationDto2Entity.instance.convert(projectTaskRelationDto));
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 添加工时
     * 只添加模板第三层包含价格职位的内容
     * 工时设定为初始化状态
     * @param jsonInfo
     * @return
     */
    public JSONObject addTask(JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        List<ProjectTaskEntity> projectTasks = jsonInfo.getJSONArray("projectTask").toJavaList(ProjectTaskEntity.class);
        for (ProjectTaskEntity projectTaskEntity : projectTasks) {
            projectTaskEntity.setTaskStatus(CommonConst.task_status_init);
            projectTaskEntity.setStatus(CommonConst.project_task_status_review);
        }
        this.saveBatch(projectTasks);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
