package com.pro.financial.management.controller.view;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceivementStatisticsView {
    // 年
    private int year;
    // 季度
    private int quarter;
    //月
    private int month;

    // 到款数量
    private int count = 0;
    // 到款金额
    private BigDecimal money = BigDecimal.ZERO;
    // 认款收入
    private BigDecimal revenue = BigDecimal.ZERO;
    // 认款押金
    private BigDecimal deposit = BigDecimal.ZERO;
    // 环比金额
    private BigDecimal chainMoney = BigDecimal.ZERO;
    // 环比增长速度
    private Integer chainGrowth;
    // 环比发展速度
    private Integer chainDevelopment;
    // 百分比年
    private Integer percentYear;
    // 百分比总
    private Integer percentTotal;
}
