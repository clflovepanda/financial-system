package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.dao.ProjectDao;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.user.dto.DataSourceDto;
import com.pro.financial.user.filter.LoginFilter;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Service
public class ProjectBiz extends ServiceImpl<ProjectDao, ProjectEntity> {
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private LoginFilter loginFilter;

    public int addProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = ProjectDto2Entity.instance.convert(projectDto);
        int count = projectDao.insert(projectEntity);
        projectDto.setProjectId(projectEntity.getProjectId());
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
        if (CollectionUtils.isEmpty(ids)) {
            return projectDao.getAllProjectList();
        } else {
            return projectDao.getProjectList(ids);
        }
    }

    public List<ProjectEntity> getList(List<Integer> projectIds, String projectNo, String projectName, String managerName, String salesName, String userNames,
                                       String settlementState, String state, String saleCommisState, Date startDate, Date endDate, String auditingState) {
        return projectDao.getList(projectIds, projectNo, projectName, managerName, salesName, userNames,
                settlementState, state, saleCommisState, startDate, endDate, auditingState);
    }

    public List<ProjectDto> getProjectByKeywords(String keyWords) {
        if (StringUtils.isNotEmpty(keyWords)) {
            String keyWord = "%" + keyWords + "%";
            return ConvertUtil.convert(ProjectEntity2Dto.instance, projectDao.getProjectByKeywords(keyWord));
        }
        return ConvertUtil.convert(ProjectEntity2Dto.instance, projectDao.getAllProjectList());
    }
}
