package com.pro.financial.management.controller.view;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceivementStatisticsView {
    // 年
    private int year;
    // 到款数量
    private int count;
    // 到款金额
    private BigDecimal money;
    // 认款收入
    private BigDecimal revenue;
    // 认款押金
    private BigDecimal deposit;
    // 环比金额
    private BigDecimal chainMoney;
    // 环比增长速度
    private Integer chainGrowth;
    // 环比发展速度
    private Integer chainDevelopment;
    // 百分比年
    private Integer percentYear;
    // 百分比总
    private Integer percentTotal;
}
