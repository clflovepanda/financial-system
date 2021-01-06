package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ExpenditureStatisticsEntity {

    // 已提交总计
    private BigDecimal submitted;
    //财务未审批总计
    private BigDecimal notApproved;
    //已支付总计
    private BigDecimal paid;
    // 票据作废总计
    private BigDecimal cancel;
    // 平借款总计
    private BigDecimal flatLoan;
}
