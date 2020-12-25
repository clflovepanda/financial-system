package com.pro.financial.management.controller.view;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReceivementView {
    private Integer id;

    // 到款账户
    private Integer companyId;
    private String companyName;

    // 到款种类
    private Integer receivementTypeId;
    private String receivementTypeName;

    // 到款金额/元
    private BigDecimal receivementMoney;

    // 汇款方类型
    private String remitterMethodId;
    private String remitterMethodName;

    // 汇款方
    private String remitter;

    // 到款时间
    private Date receiveDate;

    // 已认款金额/元
    private BigDecimal hadSubscriptionTotalMoney;

    // 最新认款时间
    private Date latestSubscriptionTime;

    // 剩余认款金额/元
    private BigDecimal remaindSubscriptionTotalMoney;

    // 状态
    private Integer state;

    // 备注
    private String remark;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;
}
