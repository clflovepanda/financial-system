package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("subscription_log")
@ToString
@Getter
@Setter
public class SubscriptionLogEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
    private String revenueRemark;
}