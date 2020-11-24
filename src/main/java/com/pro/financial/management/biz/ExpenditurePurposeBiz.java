package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditurePurposeDto2Entity;
import com.pro.financial.management.dao.ExpenditurePurposeDao;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenditurePurposeBiz {

    @Autowired
    private ExpenditurePurposeDao expenditurePurposeDao;

    public int addExpenditurePurpose(ExpenditurePurposeDto expenditurePurposeDto) {
        return expenditurePurposeDao.insert(ExpenditurePurposeDto2Entity.instance.convert(expenditurePurposeDto));
    }
}