package com.pro.financial.management.biz;

import com.pro.financial.management.converter.SubscriptionLogDto2Entity;
import com.pro.financial.management.dao.SubscriptionLogDao;
import com.pro.financial.management.dto.SubscriptionLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionLogBiz {

    @Autowired
    private SubscriptionLogDao subscriptionLogDao;

    public int addSubscriptionLog(SubscriptionLogDto subscriptionLogDto) {
        return subscriptionLogDao.insert(SubscriptionLogDto2Entity.instance.convert(subscriptionLogDto));
    }
}
