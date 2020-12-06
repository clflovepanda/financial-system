package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureMethodDto2Entity;
import com.pro.financial.management.converter.ExpenditureMethodEntity2Dto;
import com.pro.financial.management.dao.ExpenditureMethodDao;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dto.ExpenditureMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenditureMethodBiz {

    @Autowired
    private ExpenditureMethodDao expenditureMethodDao;

    public int addExpenditureMethod(ExpenditureMethodDto expenditureMethodDto) {
        return expenditureMethodDao.insert(ExpenditureMethodDto2Entity.instance.convert(expenditureMethodDto));
    }

    public List<ExpenditureMethodEntity> getList() {
        return expenditureMethodDao.getList();
    }
}
