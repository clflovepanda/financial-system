package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        QueryWrapper<ExpenditureAuditLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expenditure_id", expenditureId).eq("state", 1).orderByDesc("ctime");
        return ConvertUtil.convert(ExpenditureAuditLogEntity2Dto.instance, expenditureAuditLogDao.selectList(queryWrapper));
    }

    /**
     * 修改为失效状态
     * @param expenditureAuditLogDto
     * @return
     */
    public int remove(ExpenditureAuditLogDto expenditureAuditLogDto) {
        ExpenditureAuditLogEntity expenditureAuditLogEntity = new ExpenditureAuditLogEntity();
        expenditureAuditLogEntity.setId(expenditureAuditLogDto.getId());
        expenditureAuditLogEntity.setState(0);
        return expenditureAuditLogDao.updateById(expenditureAuditLogEntity);
    }

    public int deleteExpenditureByid(Integer expenditureId) {
        ExpenditureAuditLogEntity expenditureAuditLogEntity = new ExpenditureAuditLogEntity();
        expenditureAuditLogEntity.setState(0);
        QueryWrapper<ExpenditureAuditLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expenditure_id", expenditureId);
        return expenditureAuditLogDao.update(expenditureAuditLogEntity, queryWrapper);
    }
}
