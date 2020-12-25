package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class RevenueEntity {
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

}