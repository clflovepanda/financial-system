package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class RevenueDto {
    private Integer id;

    private String revenueNo;

    private Integer receivementId;

    private Integer revenueTypeId;

    private Integer dataSourceId;

    private Integer projectId;

    private BigDecimal cnyMoney;

    private Integer delete;

    private String remark;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    private Integer subscriptionLogId;

    private String coName;
    private String remitterMethodName;
    private String receivementTypeName;
    private String dataSourceName;
    private String username;
    private String projectName;
    private String projectNo;
    private String remitter;
    //待退回
    private BigDecimal toBeReturned;
    //退回中
    private BigDecimal returning;
    //已退回
    private BigDecimal returned;
}