package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.Converter;

public class SubscriptionLogDto2Entity implements Converter<SubscriptionLogDto, SubscriptionLogEntity> {

    public static final SubscriptionLogDto2Entity instance = new SubscriptionLogDto2Entity();
    @Override
    public SubscriptionLogEntity convert(SubscriptionLogDto SubscriptionLogDto) {
        SubscriptionLogEntity subscriptionLogEntity = new SubscriptionLogEntity();
        subscriptionLogEntity = JSONObject.parseObject(JSONObject.toJSONString(SubscriptionLogDto), SubscriptionLogEntity.class);
        return subscriptionLogEntity;
    }
}
