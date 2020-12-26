package com.pro.financial.management.biz;

import com.pro.financial.management.converter.SubscriptionLogDto2Entity;
import com.pro.financial.management.converter.SubscriptionLogEntity2Dto;
import com.pro.financial.management.dao.SubscriptionLogDao;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionLogBiz {

    @Autowired
    private SubscriptionLogDao subscriptionLogDao;

    public int addSubscriptionLog(SubscriptionLogDto subscriptionLogDto) {
        SubscriptionLogEntity subscriptionLogEntity = SubscriptionLogDto2Entity.instance.convert(subscriptionLogDto);
        int count = subscriptionLogDao.insert(subscriptionLogEntity);
        subscriptionLogDto.setId(subscriptionLogEntity.getId());
        return count;
    }

    public List<SubscriptionLogEntity> getListByReceivementIds(List<Integer> receivementIds) {
        return subscriptionLogDao.getListByReceivementIds(receivementIds);
    }

    public List<SubscriptionLogEntity> getListByProjectIds(List<Integer> projectIds) {
        return subscriptionLogDao.getListByProjectIds(projectIds);
    }

    public List<SubscriptionLogDto> getListByReceivementId(Integer receivementId) {
        return ConvertUtil.convert(SubscriptionLogEntity2Dto.instance, subscriptionLogDao.getListByProjectId(receivementId));
    }


    public int deleteByReceivementId(Integer id) {
        return subscriptionLogDao.deleteByReceivementId(id);
    }
}
