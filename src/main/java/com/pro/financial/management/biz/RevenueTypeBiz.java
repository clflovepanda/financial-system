package com.pro.financial.management.biz;

import com.pro.financial.management.converter.RevenueTypeDto2Entity;
import com.pro.financial.management.converter.RevenueTypeEntity2Dto;
import com.pro.financial.management.dao.RevenueTypeDao;
import com.pro.financial.management.dto.RevenueTypeDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueTypeBiz {

    @Autowired
    private RevenueTypeDao revenueTypeDao;

    public int addRevenueType(RevenueTypeDto revenueTypeDto) {
        return revenueTypeDao.insert(RevenueTypeDto2Entity.instance.convert(revenueTypeDto));
    }

    public List<RevenueTypeDto> getType(String keyWords) {
        return ConvertUtil.convert(RevenueTypeEntity2Dto.instance, revenueTypeDao.getType(keyWords));
    }
}
