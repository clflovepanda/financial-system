package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class SettlementDto {
    private Integer settlementId;
    private Integer projectId;
    private String settlementName;
    private String settlementNo;
    private String isLastSettlement;
    private BigDecimal settlementIncome;
    private String resourceName;
    private String resourceUrl;
    private Date createDatetime;
    private String state;
    private String DataSource;
}
