package com.pro.financial.management.converter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.dao.entity.AccountingLogEntity;
import com.pro.financial.management.dto.AccountingLogDto;
import com.pro.financial.utils.Converter;

public class AccountingLogEntity2Dto implements Converter<AccountingLogEntity, AccountingLogDto> {

    public static final AccountingLogEntity2Dto instance = new AccountingLogEntity2Dto();
    @Override
    public AccountingLogDto convert(AccountingLogEntity AccountingLogEntity) {
        AccountingLogDto accountingLogDto = new AccountingLogDto();
        accountingLogDto = JSONObject.parseObject(JSONObject.toJSONString(AccountingLogEntity), AccountingLogDto.class);
        return accountingLogDto;
    }
}
