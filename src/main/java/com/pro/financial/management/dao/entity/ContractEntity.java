package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ContractEntity {
    private Integer contractId;
    private Integer projectId;
    private String contractNo;
    private String contractName;
    private String customerName;
    private String resourceName;
    private String resourceUrl;
    private Date createDatetime;
    private String state;
}
