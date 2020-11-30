package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.AccountingLogEntity;
import com.pro.financial.management.dto.AccountingLogDto;
import com.pro.financial.utils.Converter;

public class AccountingLogDto2Entity implements Converter<AccountingLogDto, AccountingLogEntity> {

    public static final AccountingLogDto2Entity instance = new AccountingLogDto2Entity();
    @Override
    public AccountingLogEntity convert(AccountingLogDto AccountingLogDto) {
        AccountingLogEntity accountingLogEntity = new AccountingLogEntity();
        accountingLogEntity = JSONObject.parseObject(JSONObject.toJSONString(AccountingLogDto), AccountingLogEntity.class);
        return accountingLogEntity;
    }
}
