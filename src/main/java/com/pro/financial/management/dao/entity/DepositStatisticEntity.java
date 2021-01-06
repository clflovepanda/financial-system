package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class DepositStatisticEntity {
    //全部押金金额
    private BigDecimal allDeposit;
    // 待退回总押金
    private BigDecimal toBeReturned;
    // 退回审批中押金总金额
    private BigDecimal approval;
    // 已退回押金总金额
    private BigDecimal returned;
}
