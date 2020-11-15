package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProjectDataSourceEntity {
    private Integer id;

    private String projectId;

    private String dataSourceId;

    private Date ctime;
}