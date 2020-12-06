package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureDto2Entity;
import com.pro.financial.management.dao.ExpenditureDao;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenditureBiz {

    @Autowired
    private ExpenditureDao expenditureDao;

    public int addExpenditure(ExpenditureDto expenditureDto) {
        return expenditureDao.insert(ExpenditureDto2Entity.instance.convert(expenditureDto));
    }

    public List<ExpenditureEntity> getExpenditureList(List<Integer> projectIds) {
        return expenditureDao.getExpenditureList(projectIds);
    }
}
