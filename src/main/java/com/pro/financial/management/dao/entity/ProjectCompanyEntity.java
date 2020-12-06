package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProjectCompanyEntity {
    private Integer id;

    private Integer projectId;

    private Integer companyId;

    private Date ctime;
}