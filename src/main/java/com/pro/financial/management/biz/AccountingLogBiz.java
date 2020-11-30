package com.pro.financial.management.biz;

import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.AccountingLogDto2Entity;
import com.pro.financial.management.converter.ContractDto2Entity;
import com.pro.financial.management.converter.ContractEntity2Dto;
import com.pro.financial.management.dao.AccountingLogDao;
import com.pro.financial.management.dao.ContractDao;
import com.pro.financial.management.dao.entity.ContractEntity;
import com.pro.financial.management.dto.AccountingLogDto;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.utils.CommonUtil;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountingLogBiz {

    @Autowired
    private AccountingLogDao accountingLogDao;

    public int addAccountingLog(AccountingLogDto accountingLogDto) {
        return accountingLogDao.insert(AccountingLogDto2Entity.instance.convert(accountingLogDto));
    }
}
