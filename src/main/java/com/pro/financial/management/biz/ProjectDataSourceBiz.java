package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.ProjectDataSourceDto2Entity;
import com.pro.financial.management.dao.ProjectDataSourceDao;
import com.pro.financial.management.dao.ProjectUserDao;
import com.pro.financial.management.dao.entity.ProjectDataSourceEntity;
import com.pro.financial.management.dto.ProjectDataSourceDto;
import com.pro.financial.user.dto.DataSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ProjectDataSourceBiz extends ServiceImpl<ProjectDataSourceDao, ProjectDataSourceEntity> {
    @Autowired
    private ProjectDataSourceDao projectDataSourceDao;
    @Autowired
    private ProjectUserDao projectUserDao;

    public int addProjectDataSource(ProjectDataSourceDto projectDataSourceDto) {
        ProjectDataSourceEntity projectDataSourceEntity = ProjectDataSourceDto2Entity.instance.convert(projectDataSourceDto);
        int count = projectDataSourceDao.insert(projectDataSourceEntity);
        projectDataSourceDto.setId(projectDataSourceEntity.getId());
        return count;
    }

    /**
     * 根绝数据源获取项目id
     * @param request
     * @return
     */
    public List<Integer> getProjectIdsByCookie(HttpServletRequest request) {
        String datasourceJsonStr = request.getSession().getAttribute("datasource").toString();
        if (StringUtils.isEmpty(datasourceJsonStr)) {
            return null;
        }
        List<DataSourceDto> sourceDtos = JSONArray.parseArray(datasourceJsonStr, DataSourceDto.class);
        if (CollectionUtils.isEmpty(sourceDtos)) {
            return null;
        }
        List<Integer> datasourceIds = new ArrayList<>();
        Set<Integer> projectIds = new HashSet<>();
        for (DataSourceDto dataSourceDto : sourceDtos) {
            datasourceIds.add(dataSourceDto.getDataSourceId());
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("data_source_id", datasourceIds);
        List<ProjectDataSourceEntity> projectDataSourceEntities = projectDataSourceDao.selectList(queryWrapper);
        for (ProjectDataSourceEntity projectDataSourceEntity : projectDataSourceEntities) {
            projectIds.add(Integer.parseInt(projectDataSourceEntity.getProjectId()));
        }
        Cookie[] cookies = request.getCookies();
        int userId = 0;
        for (Cookie cookie : cookies) {
            if (org.apache.commons.lang3.StringUtils.equals(cookie.getName(), CommonConst.cookie_user_head)) {
                try {
                    String userJsonStr = URLDecoder.decode(cookie.getValue(), "utf-8");
                    userId = Integer.parseInt(userJsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //todo 查询项目中有本人的项目id
        List<Integer> userProjectId = projectUserDao.getProjectIdByUserId(userId);
        projectIds.addAll(userProjectId);
        return new ArrayList<>(projectIds);
    }
}
