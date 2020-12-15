package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditureTypeDto {
    private Integer expenditureTypeId;

    private Integer expenditureMethodId;

    private String expenditureTypeName;

    private String remark;
}