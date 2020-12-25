package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@ToString
@Getter
@Setter
public class ProjectAuditLogDto {
    private Integer id;

    private Integer projectId;

    private Integer auditType;

    private Integer createUser;

    private Date ctime;

    private Integer state;

    private String createUserName;
}