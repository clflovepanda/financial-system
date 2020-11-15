package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class ProjectDto {
    private Integer id;

    private String code;

    private String name;

    private String fullname;

    private Date startDate;

    private Date endDate;

    private BigDecimal estincome;

    private BigDecimal budget;

    private String description;

    private Integer state;

    private Integer auditingState;

    private Integer saleCommisState;

    private Integer settlementState;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;
}