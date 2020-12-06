package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ProjectUserDto2Entity;
import com.pro.financial.management.dao.ProjectUserDao;
import com.pro.financial.management.dao.entity.ProjectUserEntity;
import com.pro.financial.management.dto.ProjectUserDto;
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
}
