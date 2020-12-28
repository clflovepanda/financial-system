package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 押金流程表
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("deposit_log")
public class DepositLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "deposit_log_id", type = IdType.AUTO)
    private Integer depositLogId;

    /**
     * 收入表主键
     */
    private Integer revenueId;

    /**
     * 支出表主键
     */
    private Integer expenditureId;

    /**
     * 0退回中，1已经退回
     */
    private Integer state;

    /**
     * 审批人
     */
    private Integer auditUser;

    /**
     * 创建时间
     */
    private Date auditTime;


}
