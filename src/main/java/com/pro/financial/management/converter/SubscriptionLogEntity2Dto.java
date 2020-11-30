package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.Converter;

public class SubscriptionLogEntity2Dto implements Converter<SubscriptionLogEntity, SubscriptionLogDto> {

    public static final SubscriptionLogEntity2Dto instance = new SubscriptionLogEntity2Dto();
    @Override
    public SubscriptionLogDto convert(SubscriptionLogEntity SubscriptionLogEntity) {
        SubscriptionLogDto subscriptionLogDto = new SubscriptionLogDto();
        subscriptionLogDto = JSONObject.parseObject(JSONObject.toJSONString(SubscriptionLogEntity), SubscriptionLogDto.class);
        return subscriptionLogDto;
    }
}
