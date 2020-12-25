package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ExpenditureAuditLogDto2Entity;
import com.pro.financial.management.dao.ExpenditureAuditLogDao;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
