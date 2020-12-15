package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class ExpenditureProjectEntity {
    private Integer expenditureProjectId;

    private Integer expenditureId;

    private Integer projectId;

    private Date ctime;

}