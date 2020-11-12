package com.pro.financial.management.biz;

import com.pro.financial.management.converter.SettlementDto2Entity;
import com.pro.financial.management.converter.SettlementEntity2Dto;
import com.pro.financial.management.dao.SettlementDao;
import com.pro.financial.management.dao.entity.SettlementEntity;
import com.pro.financial.management.dto.SettlementDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementBiz {

    @Autowired
    private SettlementDao settlementDao;

    public List<SettlementDto> getSettlement(String settlementName, String settlementNo, Integer limit, Integer offset) {
        List<SettlementEntity> settlementEntities = settlementDao.getSettlement(settlementName, settlementNo, limit, offset);
        return ConvertUtil.convert(SettlementEntity2Dto.instance, settlementEntities);
    }

    public int addSettlement(SettlementDto settlementDto) {
        SettlementEntity settlementEntity = SettlementDto2Entity.instance.convert(settlementDto);
        return settlementDao.addSettlement(settlementEntity);
    }

    public SettlementDto getBySettlementId(int settlementId) {
        SettlementEntity settlementEntity = settlementDao.selectById(settlementId);
        return SettlementEntity2Dto.instance.convert(settlementEntity);
    }

    public int updateSettlement(SettlementDto settlementDto) {
        SettlementEntity settlementEntity = SettlementDto2Entity.instance.convert(settlementDto);
        return settlementDao.updateSettlementById(settlementEntity);
    }

    public int deleteSettlementTrue(int settlementId) {
        return settlementDao.deleteSettlement(settlementId);
    }
    public int deleteSettlement(int settlementId) {
        SettlementEntity settlementEntity = new SettlementEntity();
        settlementEntity.setSettlementId(settlementId);
        settlementEntity.setState("0");
        return settlementDao.updateSettlementById(settlementEntity);
    }
}
