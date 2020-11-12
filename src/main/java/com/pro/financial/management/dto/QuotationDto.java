package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class QuotationDto {
    private Integer quotationId;
    private Integer projectId;
    private String quotationName;
    private String quotationNo;
    private String resourceName;
    private String resourceUrl;
    private Date createDatetime;
    private String state;
}
