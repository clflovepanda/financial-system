package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProjectFinancialStatisticsDto {
    //预计收入
    private BigDecimal estincome;
    //预计支出
    private BigDecimal budget;

    //实际收入
    private BigDecimal actualIncome;
    //实际支出
    private BigDecimal actualExpenditure;

    //预收押金
    private BigDecimal deposit;
    //押金转收入
    private BigDecimal depositIncome;
    //项目利润
    private BigDecimal profit;
    //毛利率
    private double rate;
    //支出比
    private double expenditureRatio;
    //项目纯利润
    private BigDecimal relProfit;
    //纯利率
    private double relRate;
    //人工成本
    private BigDecimal timeMoney;
    //结算收入
    private BigDecimal settlement;
    //应收收入
    private BigDecimal receivable;

}
