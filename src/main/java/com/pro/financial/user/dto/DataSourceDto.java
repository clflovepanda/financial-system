package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class DataSourceDto {
    private Integer dataSourceId;
    private Integer parentId;
    private String dataSourceName;
    private Date createDatetime;
    private List<DataSourceDto> son;
}
