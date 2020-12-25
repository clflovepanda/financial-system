package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@ToString
@Getter
@Setter
public class ExpenditureAuditLogEntity {
    private Integer id;

    private Integer expenditureId;

    private Integer auditType;

    private Integer createUser;

    private Date ctime;

    private Integer state;

    private String username;
}