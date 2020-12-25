package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class SubscriptionLogEntity {
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