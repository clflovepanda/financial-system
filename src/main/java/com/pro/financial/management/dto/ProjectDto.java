package com.pro.financial.management.dto;

import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dao.entity.RevenueEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ProjectDto {
    private Integer projectId;

    private String code;

    private String name;

    private String fullname;

    private Date startDate;

    private Date endDate;

    private BigDecimal estincome;

    private BigDecimal budget;

    private String description;

    private Integer state;

    private Integer auditingState;

    private Integer saleCommisState;

    private Integer settlementState;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    //项目经理
    private String managerName;

    //销售经理
    private String salesName;

    private String userNames;

    //花费工时
    private BigDecimal takeTime;

    //结算收入
    private BigDecimal settlementIncome;
    //结算支出
    private BigDecimal settlementExpenses;

    //收付款收入
    private BigDecimal paymentIncome;
    //收付款支出
    private BigDecimal paymentExpenses;
    //收付款利润
    private Double paymentProfit;
    //项目毛利润
    private BigDecimal projectProfit;
    //项目毛利率
    private Double projectRate;

    private String dataSourceName;
    private String dataSourceId;

    private Integer companyId;
    private Integer managerId;
    private Integer salesId;
    private List<Integer> userIds;

    //大收
    private BigDecimal relRevenue;
    //大支
    private BigDecimal relExpenditure;
    private BigDecimal receivable;


    private Integer limit;
    private Integer offset;
}