package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.converter.ProjectUserDto2Entity;
import com.pro.financial.management.converter.ProjectUserEntity2Dto;
import com.pro.financial.management.dao.ProjectUserDao;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dao.entity.ProjectUserEntity;
import com.pro.financial.management.dto.ProjectUserDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProjectUserBiz {
    @Autowired
    private ProjectUserDao projectUserDao;

    public int addProjectUser(ProjectUserDto projectUserDto) {
        ProjectUserEntity projectUserEntity = ProjectUserDto2Entity.instance.convert(projectUserDto);
        int count = projectUserDao.insert(projectUserEntity);
        projectUserDto.setId(projectUserEntity.getId());
        return count;
    }

    public int batchAddProjectUser(List<ProjectUserDto> projectUserDtos) {
        List<ProjectUserEntity> projectUserEntities = new ArrayList<>(projectUserDtos.size());
        for (ProjectUserDto projectUserDto : projectUserDtos) {
            projectUserEntities.add(ProjectUserDto2Entity.instance.convert(projectUserDto));
        }
        int count = projectUserDao.batchInsert(projectUserEntities);
        return count;
    }

    public List<ProjectUserEntity> getProjectUserList(List<Integer> projectIds) {
        return projectUserDao.getPrjectUserList(projectIds);
    }

    public int batchUpdateProjectUser(List<ProjectUserDto> projectUserDtos) {
        int count = 0;
        projectUserDao.batchInsert(ConvertUtil.convert(ProjectUserDto2Entity.instance, projectUserDtos));
        return count;
    }

    public int deleteByProjectId(int projectId) {
        if (projectId < 0) {
            return 0;
        }
        QueryWrapper<ProjectUserEntity> wrapper = new QueryWrapper();
        wrapper.eq("project_id", projectId);
        return projectUserDao.delete(wrapper);
    }
}
