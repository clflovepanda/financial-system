package com.pro.financial.management.biz;

import com.pro.financial.management.converter.RevenueDto2Entity;
import com.pro.financial.management.converter.RevenueEntity2Dto;
import com.pro.financial.management.dao.RevenueDao;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RevenueBiz {

    @Autowired
    private RevenueDao revenueDao;

    public int addRevenue(RevenueDto revenueDto) {
        return revenueDao.insert(RevenueDto2Entity.instance.convert(revenueDto));
    }

    public List<RevenueEntity> getRevenueList(List<Integer> projectIds) {
        return revenueDao.getRevenueList(projectIds);
    }

    public List<RevenueDto> searchList(String projectId, String revenueNo, String remitterMethodId, String receivementTypeId, String companyId, String remitter, String createUser, Date startDate, Date endDate) {
        return ConvertUtil.convert(RevenueEntity2Dto.instance, revenueDao.searchList(projectId, revenueNo, remitterMethodId, receivementTypeId, companyId, remitter, createUser, startDate, endDate));
    }
}
