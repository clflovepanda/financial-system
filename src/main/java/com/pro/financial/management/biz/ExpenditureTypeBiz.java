package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureTypeDto2Entity;
import com.pro.financial.management.dao.ExpenditureTypeDao;
import com.pro.financial.management.dto.ExpenditureTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenditureTypeBiz {

    @Autowired
    private ExpenditureTypeDao expenditureTypeDao;

    public int addExpenditureType(ExpenditureTypeDto expenditureTypeDto) {
        return expenditureTypeDao.insert(ExpenditureTypeDto2Entity.instance.convert(expenditureTypeDto));
    }
}
