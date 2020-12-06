package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.ProjectDataSourceDto2Entity;
import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.dao.ProjectDao;
import com.pro.financial.management.dao.ProjectDataSourceDao;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectDataSourceBiz {
    @Autowired
    private ProjectDataSourceDao projectDataSourceDao;

    public int addProjectDataSource(ProjectDataSourceDto projectDataSourceDto) {
        ProjectDataSourceEntity projectDataSourceEntity = ProjectDataSourceDto2Entity.instance.convert(projectDataSourceDto);
        int count = projectDataSourceDao.insert(projectDataSourceEntity);
        projectDataSourceDto.setId(projectDataSourceEntity.getId());
        return count;
    }
}
