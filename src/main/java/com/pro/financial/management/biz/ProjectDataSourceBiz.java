package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.ProjectDataSourceDto2Entity;
import com.pro.financial.management.dao.ProjectDataSourceDao;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import com.pro.financial.user.dto.DataSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProjectDataSourceBiz extends ServiceImpl<ProjectDataSourceDao, ProjectDataSourceEntity> {
    @Autowired
    private ProjectDataSourceDao projectDataSourceDao;

    public int addProjectDataSource(ProjectDataSourceDto projectDataSourceDto) {
        ProjectDataSourceEntity projectDataSourceEntity = ProjectDataSourceDto2Entity.instance.convert(projectDataSourceDto);
        int count = projectDataSourceDao.insert(projectDataSourceEntity);
        projectDataSourceDto.setId(projectDataSourceEntity.getId());
        return count;
    }

    /**
     * 根绝数据源获取项目id
     * @param sourceDtos
     * @return
     */
    public List<Integer> getProjectIdsByCookie(List<DataSourceDto> sourceDtos) {
        if (CollectionUtils.isEmpty(sourceDtos)) {
            return null;
        }
        List<Integer> datasourceIds = new ArrayList<>();
        List<Integer> projectIds = new ArrayList<>();
        for (DataSourceDto dataSourceDto : sourceDtos) {
            datasourceIds.add(dataSourceDto.getDataSourceId());
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("data_source_id", datasourceIds);
        List<ProjectDataSourceEntity> projectDataSourceEntities = projectDataSourceDao.selectList(queryWrapper);
        for (ProjectDataSourceEntity projectDataSourceEntity : projectDataSourceEntities) {
            projectIds.add(Integer.parseInt(projectDataSourceEntity.getProjectId()));
        }
        return projectIds;
    }
}
