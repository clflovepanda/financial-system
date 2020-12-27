package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 应收单表
 * </p>
 *
 * @author panda
 * @since 2020-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("receivable")
@Getter
@Setter
@ToString
public class ReceivableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "receivable_id", type = IdType.AUTO)
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
    private Date applyDatetime;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 备用字段
     */
    private String exattribute;

    private String username;


}
