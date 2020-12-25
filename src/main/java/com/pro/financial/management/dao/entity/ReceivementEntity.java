package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class ReceivementEntity {
    private Integer id;

    private Integer companyId;

    private Integer receivementTypeId;

    private BigDecimal receivementMoney;

    private String remitterMethodId;

    private String remitter;

    private Date receiveDate;

    private Integer state;

    private String remark;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;
    //公司名称
    private String coName;
    private String receivementTypeName;
    private String remitterMethodName;
}