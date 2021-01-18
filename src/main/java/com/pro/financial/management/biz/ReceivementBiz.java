package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ReceivementDto2Entity;
import com.pro.financial.management.dao.ReceivementDao;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dto.ReceivementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReceivementBiz {

    @Autowired
    private ReceivementDao receivementDao;

    public int addReceivement(ReceivementDto receivementDto) {
        return receivementDao.insert(ReceivementDto2Entity.instance.convert(receivementDto));
    }

    public int updateReceivement(ReceivementDto receivementDto) {
        return receivementDao.update(ReceivementDto2Entity.instance.convert(receivementDto));
    }

    public List<ReceivementEntity> getListById(List<Integer> ids) {
        return receivementDao.getList(ids, null, null, null, null, null, null, null, null);
    }

    public ReceivementEntity getById(Integer id) {
        return receivementDao.getById(id);
    }

    public int updateReceivementState(Integer id , Integer state) {
        return receivementDao.updatestate(id, state);
    }

    public List<ReceivementEntity> getAllList() {
        return receivementDao.getAllList();
    }

    public List<ReceivementEntity> getList(List<Integer> ids, String companyId, String receivementTypeId, String remitterMethodId,
                                           String remitter, Date startDate, Date endDate, Integer limit, Integer offset) {
        return receivementDao.getList(ids, companyId, receivementTypeId, remitterMethodId, remitter, startDate, endDate, limit, offset);
    }

    public List<ReceivementEntity> statistics(String dataSourceId, String revenueTypeId, String projectName, Date startDate, Date endDate) {
        return receivementDao.statistics(dataSourceId, revenueTypeId, projectName, startDate, endDate);
    }

    public int getCount(String companyId, String receivementTypeId, String remitterMethodId, String remitter, Date startDate, Date endDate) {
        return receivementDao.getCount(companyId, receivementTypeId, remitterMethodId, remitter, startDate, endDate);
    }

    public List<ReceivementEntity> statisticsDetail(Date startDate, Date endDate) {
        return receivementDao.statisticsDetail(startDate, endDate);
    }
}
