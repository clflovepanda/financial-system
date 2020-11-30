package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.dao.ProjectDao;
import com.pro.financial.management.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectBiz {
    @Autowired
    private ProjectDao projectDao;

    public int addProject(ProjectDto projectDto) {
        return projectDao.insert(ProjectDto2Entity.instance.convert(projectDto));
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
}
