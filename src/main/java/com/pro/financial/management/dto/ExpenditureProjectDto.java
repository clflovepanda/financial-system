package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class ExpenditureProjectDto {
    private Integer expenditureProjectid;

    private Integer expenditureId;

    private Integer projectId;

    private Date ctime;

}