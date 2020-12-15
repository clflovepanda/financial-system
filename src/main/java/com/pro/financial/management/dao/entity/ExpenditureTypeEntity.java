package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditureTypeEntity {
    private Integer expenditureTypeId;

    private Integer expenditureMethodId;

    private String expenditureTypeName;

    private String remark;

}