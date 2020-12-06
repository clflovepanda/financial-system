package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProjectDataSourceDto {
    private Integer id;

    private String projectId;

    private String dataSourceId;

    private Date ctime;
}