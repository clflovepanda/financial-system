package com.pro.financial.management.biz;

import com.pro.financial.management.converter.RevenueDto2Entity;
import com.pro.financial.management.dao.RevenueDao;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dto.RevenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
