package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class ExpenditureAuditLogDto {
    private Integer id;

    private Integer expenditureId;

    private Integer auditType;

    private Integer createUser;

    private Date ctime;

    private Integer state;

    private String username;

    private String remark;
}