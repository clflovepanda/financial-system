package com.pro.financial.management.biz;

import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.RevenueDto2Entity;
import com.pro.financial.management.converter.RevenueEntity2Dto;
import com.pro.financial.management.dao.ProjectDao;
import com.pro.financial.management.dao.RevenueDao;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.CommonUtil;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RevenueBiz {

    @Autowired
    private RevenueDao revenueDao;
    @Autowired
    private ProjectDao projectDao;

    public int addRevenue(RevenueDto revenueDto) {
        return revenueDao.insert(RevenueDto2Entity.instance.convert(revenueDto));
    }

    public List<RevenueEntity> getRevenueList(List<Integer> projectIds) {
        return revenueDao.getRevenueList(projectIds);
    }

    public List<RevenueDto> searchList(String projectId, String revenueNo, String remitterMethodId, String receivementTypeId, String companyId, String remitter, String createUser, Date startDate, Date endDate) {
        return ConvertUtil.convert(RevenueEntity2Dto.instance, revenueDao.searchList(projectId, revenueNo, remitterMethodId, receivementTypeId, companyId, remitter, createUser, startDate, endDate));
    }

    public int addRevenueBySubLog(SubscriptionLogDto subscriptionLogDto, Integer userId) {
        int projectId = subscriptionLogDto.getProjectId();
        ProjectEntity projectEntity =  projectDao.getProjectById(projectId);
        //生成编号
        String revenueNo;
        String lastNo = revenueDao.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            lastNo = "001";
        }
        revenueNo = CommonUtil.generatorNO(CommonConst.initials_revenue, projectEntity.getDataSourceName(), lastNo);
        RevenueEntity revenueEntity = new RevenueEntity();
        revenueEntity.setSubscriptionLogId(subscriptionLogDto.getId() == null ? 0 : subscriptionLogDto.getId());
        revenueEntity.setRevenueNo(revenueNo);
        revenueEntity.setCnyMoney(subscriptionLogDto.getReceivementMoney());
        revenueEntity.setReceivementId(subscriptionLogDto.getReceivementId());
        revenueEntity.setRevenueTypeId(subscriptionLogDto.getRevenueTypeId());
        revenueEntity.setProjectId(projectId);
        revenueEntity.setDataSourceId(projectEntity.getDataSourceId());
        revenueEntity.setRemark(subscriptionLogDto.getRemark());
        revenueEntity.setCreateUser(userId);
        revenueEntity.setCtime(new Date());
        revenueEntity.setUpdateUser(userId);
        revenueEntity.setUtime(new Date());
        return revenueDao.insert(revenueEntity);
    }
}
