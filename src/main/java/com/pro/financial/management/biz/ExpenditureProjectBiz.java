package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureProjectDto2Entity;
import com.pro.financial.management.dao.ExpenditureProjectDao;
import com.pro.financial.management.dto.ExpenditureProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenditureProjectBiz {

    @Autowired
    private ExpenditureProjectDao expenditureProjectDao;

    public int addExpenditureProject(ExpenditureProjectDto expenditureProjectDto) {
        return expenditureProjectDao.insert(ExpenditureProjectDto2Entity.instance.convert(expenditureProjectDto));
    }
}
