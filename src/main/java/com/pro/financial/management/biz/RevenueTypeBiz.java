package com.pro.financial.management.biz;

import com.pro.financial.management.converter.RevenueTypeDto2Entity;
import com.pro.financial.management.dao.RevenueTypeDao;
import com.pro.financial.management.dto.RevenueTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevenueTypeBiz {

    @Autowired
    private RevenueTypeDao revenueTypeDao;

    public int addRevenueType(RevenueTypeDto revenueTypeDto) {
        return revenueTypeDao.insert(RevenueTypeDto2Entity.instance.convert(revenueTypeDto));
    }
}
