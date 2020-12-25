package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ProjectCompanyDto2Entity;
import com.pro.financial.management.converter.ProjectDataSourceDto2Entity;
import com.pro.financial.management.dao.ProjectCompanyDao;
import com.pro.financial.management.dao.ProjectDataSourceDao;
import com.pro.financial.management.dao.entity.ProjectCompanyEntity;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dto.ProjectCompanyDto;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectCompanyBiz {
    @Autowired
    private ProjectCompanyDao projectCompanyDao;

    public int addProjectCompany(ProjectCompanyDto projectCompanyDto) {
        ProjectCompanyEntity projectCompanyEntity = ProjectCompanyDto2Entity.instance.convert(projectCompanyDto);
        int count = projectCompanyDao.insert(projectCompanyEntity);
        projectCompanyDto.setId(projectCompanyEntity.getId());
        return count;
    }

    public String getCompanyByProjectId(Integer id) {
        return projectCompanyDao.getCompanyByProjectId(id);
    }
}
