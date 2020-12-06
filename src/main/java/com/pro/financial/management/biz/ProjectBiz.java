package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.dao.ProjectDao;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectBiz extends ServiceImpl<ProjectDao, ProjectEntity> {
    @Autowired
    private ProjectDao projectDao;

    public int addProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = ProjectDto2Entity.instance.convert(projectDto);
        int count = projectDao.insert(projectEntity);
        projectDto.setId(projectEntity.getId());
        return count;
    }

    public int updateAuditState(Integer id, Integer auditState) {
        return projectDao.updateAuditState(id, auditState);
    }

    public int updateSaleCommisState(Integer id, Integer saleCommisState) {
        return projectDao.updateSaleCommisState(id, saleCommisState);
    }

    public int updateState(Integer id, Integer state) {
        return projectDao.updateState(id, state);
    }

    public List<ProjectEntity> getProjectList(List<Integer> ids) {
        return projectDao.getProjectList(ids);
    }
}
