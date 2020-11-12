package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class SettlementEntity {
    private Integer settlementId;
    private Integer projectId;
    private String settlementNo;
    private String isLastSettlement;
    private BigDecimal settlementIncom;
    private String resourceName;
    private String resourceUrl;
    private Date createDatetime;
    private String state;
}
