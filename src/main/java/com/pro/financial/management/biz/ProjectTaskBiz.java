package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.*;
import com.pro.financial.management.dao.ProjectTaskDao;
import com.pro.financial.management.dao.ProjectTaskRelationDao;
import com.pro.financial.management.dao.ProjectUserDao;
import com.pro.financial.management.dao.TemplateDao;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.ProjectTaskDto;
import com.pro.financial.management.dto.ProjectTaskRelationDto;
import com.pro.financial.management.dto.ProjectUserDto;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dto.UserDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private ProjectUserDao projectUserDao;

    @Autowired
    private UserDao userDao;

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
        List<ProjectTaskRelationDto> projectTaskRelationDtos = this.getTaskRelation(projectTaskRelationDto.getProjectId());
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", projectTaskRelationDtos);
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

    /**
     * 添加人员(有id的修改 没有id的新增)
     * @param projectTaskDtos
     * @return
     */
    public JSONObject addUser(List<ProjectTaskDto> projectTaskDtos) {
        JSONObject result = new JSONObject();
        List<ProjectTaskDto> projectTaskWithTaskId = new LinkedList<>();
        List<ProjectTaskDto> projectTaskWithoutTaskId = new LinkedList<>();
        if (CollectionUtils.isEmpty(projectTaskDtos)) {
            result.put("code", 0);
            result.put("msg", "");
            return result;
        }
        for (ProjectTaskDto projectTaskDto : projectTaskDtos) {
            projectTaskDto.setStatus(CommonConst.project_task_status_review);
            projectTaskDto.setTaskStatus(CommonConst.task_status_init);
            if (projectTaskDto.getTaskId() != null && projectTaskDto.getTaskId() > 1) {
                projectTaskWithTaskId.add(projectTaskDto);
            } else {
                projectTaskWithoutTaskId.add(projectTaskDto);
            }
        }
        this.saveBatch(ConvertUtil.convert(ProjectTaskDto2Entity.instance, projectTaskWithoutTaskId));
        this.updateBatchById(ConvertUtil.convert(ProjectTaskDto2Entity.instance, projectTaskWithTaskId));

        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    /**
     * 初始化工时
     * @param projectTaskDto
     * @return
     */
    public JSONObject initTask(ProjectTaskDto projectTaskDto) {
        JSONObject result = new JSONObject();
        Map<String,Object> resutlMap = new HashMap<>();
        if (projectTaskDto.getTaskRelationId() == null || projectTaskDto.getTaskRelationId() < 1) {
            result.put("code", 1001);
            result.put("msg", "未传入关联id");
            return result;
        }
        if (projectTaskDto.getTemplateId() == null || projectTaskDto.getTemplateId() < 1) {
            result.put("code", 1001);
            result.put("msg", "未获取到模板id");
            return result;
        }

        TemplateEntity templateEntity = templateDao.selectById(projectTaskDto.getTemplateId());
        QueryWrapper<ProjectTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_relation_id", projectTaskDto.getTaskRelationId()).eq("template_id", projectTaskDto.getTemplateId())
                .eq("status", CommonConst.project_task_status_template).eq("task_status", CommonConst.task_status_init);
        List<ProjectTaskEntity> projectTaskEntities = projectTaskDao.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(projectTaskEntities)) {
            resutlMap.put("tasktemp", projectTaskEntities.get(0));
        } else {
            ProjectTaskEntity projectTaskEntity = ProjectTaskDto2Entity.instance.convert(projectTaskDto);
            projectTaskEntity.setStatus(CommonConst.project_task_status_template);
            projectTaskEntity.setTaskStatus(CommonConst.task_status_init);
            projectTaskEntity.setTakeTime(templateEntity.getTakeTime());
            projectTaskEntity.setAmount(templateEntity.getQuantity()+"");
            projectTaskDao.insert(projectTaskEntity);
            resutlMap.put("tasktemp", projectTaskEntity);
        }

        QueryWrapper<ProjectTaskEntity> addUserWrapper = new QueryWrapper<>();
        addUserWrapper.eq("task_relation_id", projectTaskDto.getTaskRelationId()).eq("template_id", projectTaskDto.getTemplateId())
                .eq("status", CommonConst.project_task_status_review).eq("task_status", CommonConst.task_status_init);
        List<ProjectTaskEntity> addUserEntities = projectTaskDao.selectList(addUserWrapper);
        for (ProjectTaskEntity projectTaskEntity : addUserEntities) {
            projectTaskEntity.setUser(userDao.getUserById(projectTaskEntity.getUserId()));
        }
        resutlMap.put("task", addUserEntities);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resutlMap);
        return result;
    }

    /**
     * 修改工时限定的数量和单位工时
     * @param projectTaskDto
     * @return
     */
    public JSONObject updateTaskTemplate(ProjectTaskDto projectTaskDto) {
        JSONObject result = new JSONObject();
        if (projectTaskDto.getTaskId() == null || projectTaskDto.getTaskId() < 1) {
            result.put("code", 1001);
            result.put("msg", "未获取到模板工时id");
            return result;
        }
        if (projectTaskDto.getAmount() == null || projectTaskDto.getTakeTime() == null) {
            result.put("code", 1001);
            result.put("msg", "数量或单位工时未传入");
            return result;
        }
        projectTaskDao.updateById(ProjectTaskDto2Entity.instance.convert(projectTaskDto));
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    public JSONObject taskList(ProjectTaskDto projectTaskDto) {
        JSONObject result = new JSONObject();
        QueryWrapper<ProjectTaskEntity> queryWrapper = new QueryWrapper<>();
        ProjectTaskEntity projectTaskEntity = ProjectTaskDto2Entity.instance.convert(projectTaskDto);
        List<ProjectTaskEntity> projectTaskEntities = projectTaskDao.taskList(projectTaskEntity);
        Page<ProjectTaskDto> page = new Page<>();

        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    public List<ProjectTaskEntity> getListByProjectIds(List<Integer> projectIds) {
        return projectTaskDao.getListByProjectIds(projectIds);
    }

    public List<ProjectTaskRelationDto> getTaskRelation(Integer projectId) {
        QueryWrapper<ProjectTaskRelationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", projectId);
        List<ProjectTaskRelationEntity> projectTaskRelationEntities = projectTaskRelationDao.selectList(queryWrapper);
        return ConvertUtil.convert(ProjectTaskRelationEntity2Dto.instance, projectTaskRelationEntities);
    }

    public List<ProjectUserDto> getProjectUsers(Integer projectId) {


        List<ProjectUserEntity> prjectUserList = projectId == 0 ? projectUserDao.getAllUser() : projectUserDao.getPrjectUserListById(projectId);
        return ConvertUtil.convert(ProjectUserEntity2Dto.instance, prjectUserList);
    }

    public List<ProjectTaskDto> gettask(Integer projectId, Integer userId, Integer taskRelationId) {
        List<ProjectTaskEntity> projectTaskEntities = projectTaskDao.gettask(projectId, userId, taskRelationId);

        return ConvertUtil.convert(ProjectTaskEntity2Dto.instance, projectTaskEntities);
    }
}
