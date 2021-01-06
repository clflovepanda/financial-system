package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class RevenueStatisticEntity {
    //全部收入
    private BigDecimal allRevenue;
    // 押金转收入
    private BigDecimal depositToRevenue;
    // 正常收入
    private BigDecimal revenue;
    // 收回押金
    private BigDecimal deposit;
}
