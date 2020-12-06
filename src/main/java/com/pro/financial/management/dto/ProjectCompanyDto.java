package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProjectCompanyDto {
    private Integer id;

    private Integer projectId;

    private Integer companyId;

    private Date ctime;
}