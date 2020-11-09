package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DataSourceDto {
    private Integer dataSourceId;
    private Integer parentId;
    private String name;
    private Date createDatetime;
}
