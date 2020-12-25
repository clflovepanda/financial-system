package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureAuditLogDto2Entity;
import com.pro.financial.management.converter.ExpenditureAuditLogEntity2Dto;
import com.pro.financial.management.dao.ExpenditureAuditLogDao;
import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenditureAuditLogBiz {
    @Autowired
    private ExpenditureAuditLogDao expenditureAuditLogDao;

    public int addExpenditureAuditLog(ExpenditureAuditLogDto expenditureAuditLogDto) {
        return expenditureAuditLogDao.insert(ExpenditureAuditLogDto2Entity.instance.convert(expenditureAuditLogDto));
    }

    public String getLastLog(Integer expenditureId) {
        return expenditureAuditLogDao.getLastLog(expenditureId);
    }

    public List<ExpenditureAuditLogDto> getLogByEId(Integer expenditureId) {
        return ConvertUtil.convert(ExpenditureAuditLogEntity2Dto.instance, expenditureAuditLogDao.getLogByEId(expenditureId));
    }
}
