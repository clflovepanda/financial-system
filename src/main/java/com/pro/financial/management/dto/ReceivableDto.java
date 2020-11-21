package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReceivableDto {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer receivableId;

    /**
     * 项目主键ID
     */
    private Integer projectId;

    /**
     * 经办人id
     */
    private Integer userId;

    /**
     * 应收单编号
     */
    private String receivableNo;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 单位
     */
    private String org;

    /**
     * 纳税人识别号
     */
    private String taxpayerNo;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 账号
     */
    private String account;

    /**
     * 开户行
     */
    private String openBank;

    /**
     * 应税劳务名称
     */
    private String taxableServiceName;

    /**
     * 开票金额/元
     */
    private BigDecimal invoiceAmount;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否有效1有效0失效
     */
    private String state;

    /**
     * 申请日期
     */
    private LocalDateTime applyDatetime;

    /**
     * 创建时间
     */
    private LocalDateTime createDatetime;

    /**
     * 备用字段
     */
    private String exattribute;

    private String username;
    private String dataSource;
}

