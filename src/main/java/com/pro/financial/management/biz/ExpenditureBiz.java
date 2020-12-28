package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureDto2Entity;
import com.pro.financial.management.converter.ExpenditureEntity2Dto;
import com.pro.financial.management.dao.ExpenditureDao;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExpenditureBiz {

    @Autowired
    private ExpenditureDao expenditureDao;

    public int addExpenditure(ExpenditureDto expenditureDto) {
        ExpenditureEntity expenditureEntity = ExpenditureDto2Entity.instance.convert(expenditureDto);
        int count = expenditureDao.insert(expenditureEntity);
        expenditureDto.setExpenditureId(expenditureEntity.getExpenditureId());
        //添加支出编号
        return count;
    }

    public List<ExpenditureEntity> getExpenditureList(List<Integer> projectIds) {
        return expenditureDao.getExpenditureList(projectIds);
    }

    public List<ExpenditureDto> statistics(String attribute, String company, String projectNo, String applyUser, String purpose, String state, String beneficiaryUnit, Date startDate, Date endDate) {
        List<ExpenditureEntity> expenditureEntities = expenditureDao.statistics(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit, startDate, endDate);
        return ConvertUtil.convert(ExpenditureEntity2Dto.instance, expenditureEntities);
    }

    public int updateExpenditure(ExpenditureDto expenditureDto) {
        return expenditureDao.updateById(ExpenditureDto2Entity.instance.convert(expenditureDto));
    }

    public List<ExpenditureDto> searchList(String projectId, String companyId, String numbering, String expenditureMethodId,
                                           String expenditureTypeId, String beneficiaryUnit, String createUser, String state,
                                           String expenditureAuditLog, String expenditurePurposeId, Date startDate, Date endDate, String keyWord) {
        return ConvertUtil.convert(ExpenditureEntity2Dto.instance, expenditureDao.searchList(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord));
    }

    public String selectLastNo() {
        return expenditureDao.selectLastNo();
    }
}
