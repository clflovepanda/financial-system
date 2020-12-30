package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@ToString
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("expenditure_audit_log")
public class ExpenditureAuditLogEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer expenditureId;

    private Integer auditType;

    private Integer createUser;

    private Date ctime;

    private Integer state;

    @TableField(exist = false)
    private String username;
    private String remark;
}