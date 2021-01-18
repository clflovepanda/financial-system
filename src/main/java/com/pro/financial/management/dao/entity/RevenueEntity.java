package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("revenue")
public class RevenueEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String revenueNo;

    private Integer receivementId;

    private Integer revenueTypeId;

    private Integer dataSourceId;

    private Integer projectId;

    private BigDecimal cnyMoney;

    @TableField(exist = false)
    private Integer delete;

    private String remark;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    private Integer subscriptionLogId;
    @TableField(exist = false)
    private String coName;
    @TableField(exist = false)
    private String remitterMethodName;
    @TableField(exist = false)
    private String receivementTypeName;
    @TableField(exist = false)
    private String dataSourceName;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String remitter;
    @TableField(exist = false)
    private String projectName;
    @TableField(exist = false)
    private String projectNo;

    //待退回
    @TableField(exist = false)
    private BigDecimal toBeReturned;
    //退回中
    @TableField(exist = false)
    private BigDecimal returning;
    //已退回
    @TableField(exist = false)
    private BigDecimal returned;

    @TableField(exist = false)
    private String revenueTypeName;

}