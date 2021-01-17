package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

@Getter
@Setter
@ToString
public class InvoiceDto {

    private Integer invoiceId;

    /**
     * 公司表_主键ID
     */
    private Integer companyId;

    /**
     * 所属项目表_主键ID
     */
    private Integer projectId;

    /**
     * 收入表_主键ID
     */
    private Integer revenueId;

    /**
     * 编号
     */
    private String invoiceNo;

    /**
     * 类型(专票 普票)
     */
    private String invoiceType;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 纳税人识别号
     */
    private String taxpayerIdentityNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 开户行
     */
    private String openingBank;

    /**
     * 账号
     */
    private String accountNumber;

    /**
     * 应税劳务名称ID
     */
    private Integer revenueTypeId;

    /**
     * 金额
     */
    private BigDecimal cnyMoney;

    /**
     * 金额大写
     */
    private String moneyCapital;

    /**
     * 申领部门
     */
    private Integer departmentId;

    /**
     * 经办人
     */
    private Integer operator;

    /**
     * 序列
     */
    private Integer sequence;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 发票备注
     */
    private String comment;

    private String username;

    private String companyName;

    private String revenueTypeName;
    private String projectName;
}
