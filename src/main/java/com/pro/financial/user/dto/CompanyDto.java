package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CompanyDto {
    private Integer CompanyId;
    private String coName;
    private String coCode;
    private String isDefault;
    private String description;
    private String state;
    private String taxis;
    private Date createDatetime;
}