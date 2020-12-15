package com.pro.financial.user.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DataSourceEntity {
    private Integer dataSourceId;
    private Integer parentId;
    private String dateSourceName;
    private Date createDatetime;
}
