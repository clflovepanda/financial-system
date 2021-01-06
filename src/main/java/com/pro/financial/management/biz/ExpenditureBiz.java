package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.converter.ExpenditureDto2Entity;
import com.pro.financial.management.converter.ExpenditureEntity2Dto;
import com.pro.financial.management.dao.ExpenditureDao;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.user.dao.CompanyDao;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExpenditureBiz {

    @Autowired
    private ExpenditureDao expenditureDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;

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

    public List<ExpenditureDto> statistics(String attribute, String company, String projectNo, String applyUser, String purpose,
                                           String state, String beneficiaryUnit, Date startDate, Date endDate, Integer limit, Integer offset) {
        List<ExpenditureEntity> expenditureEntities = expenditureDao.statistics(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit,
                startDate, endDate, limit, offset);
        return ConvertUtil.convert(ExpenditureEntity2Dto.instance, expenditureEntities);
    }

    public int statisticsCount(String attribute, String company, String projectNo, String applyUser, String purpose, String state, String beneficiaryUnit, Date startDate, Date endDate) {
        return expenditureDao.statisticsCount(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit, startDate, endDate);
    }

    public int updateExpenditure(ExpenditureDto expenditureDto) {
        return expenditureDao.updateById(ExpenditureDto2Entity.instance.convert(expenditureDto));
    }

    public List<ExpenditureDto> searchList(String projectId, String companyId, String numbering, String expenditureMethodId,
                                           String expenditureTypeId, String beneficiaryUnit, String createUser, String state,
                                           String expenditureAuditLog, String expenditurePurposeId, Date startDate, Date endDate, String keyWord,
                                           String projectName, String projectNo) {
        return ConvertUtil.convert(ExpenditureEntity2Dto.instance, expenditureDao.searchList(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord,projectName, projectNo));
    }

    public String selectLastNo() {
        return expenditureDao.selectLastNo();
    }

    public List<ExpenditureEntity> selectListByIds(List<Integer> expenditureIds) {
        if (CollectionUtils.isEmpty(expenditureIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<ExpenditureEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("expenditure_id", expenditureIds).eq("is_effective", 1);
        List<ExpenditureEntity> expenditureEntities = expenditureDao.selectList(queryWrapper);
        for (ExpenditureEntity expenditureEntity : expenditureEntities) {
            expenditureEntity.setUsername(userDao.selectById(expenditureEntity.getCreateUser()).getUsername());
            expenditureEntity.setCoName(companyDao.selectById(expenditureEntity.getCompanyId()).getCoName());
        }
        return expenditureEntities;
    }

    public ExpenditureEntity selectById(Integer expenditureId) {
        return expenditureDao.selectById(expenditureId);
    }

    public int deleteExpenditureByid(Integer expenditureId) {
        ExpenditureEntity expenditureEntity = new ExpenditureEntity();
        expenditureEntity.setExpenditureId(expenditureId);
        expenditureEntity.setIsEffective(0);
        return expenditureDao.updateById(expenditureEntity);
    }
}
