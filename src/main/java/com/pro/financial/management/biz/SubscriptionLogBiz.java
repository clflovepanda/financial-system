package com.pro.financial.management.biz;

import com.pro.financial.management.converter.SubscriptionLogDto2Entity;
import com.pro.financial.management.dao.SubscriptionLogDao;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionLogBiz {

    @Autowired
    private SubscriptionLogDao subscriptionLogDao;

    public int addSubscriptionLog(SubscriptionLogDto subscriptionLogDto) {
        return subscriptionLogDao.insert(SubscriptionLogDto2Entity.instance.convert(subscriptionLogDto));
    }

    public List<SubscriptionLogEntity> getListByReceivementIds(List<Integer> receivementIds) {
        return subscriptionLogDao.getListByReceivementIds(receivementIds);
    }

    public List<SubscriptionLogEntity> getListByProjectIds(List<Integer> projectIds) {
        return subscriptionLogDao.getListByProjectIds(projectIds);
    }
}
