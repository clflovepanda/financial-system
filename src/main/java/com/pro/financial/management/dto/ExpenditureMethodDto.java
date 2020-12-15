package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditureMethodDto {
    private Integer expenditureMethodId;

    private String expenditureMethodName;

    private String remark;

}