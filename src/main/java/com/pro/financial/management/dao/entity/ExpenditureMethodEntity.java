package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditureMethodEntity {
    private Integer expenditureMethodId;

    private String expenditureMethodName;

    private String remark;

}