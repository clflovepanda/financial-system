package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class SubscriptionLogDto {
    private Integer id;

    private Integer receivementId;

    private Integer revenueTypeId;

    private Integer projectId;

    private BigDecimal receivementMoney;

    private Date subscriptionDate;

    private Integer state;

    private String remark;

    private Integer createUser;

    private Date ctime;

    private String username;
    private String projectName;
    private String dataSourceName;
    private String revenueTypeName;
}