package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditurePurposeDto {
    private Integer expenditurePurposeId;

    private Integer expenditureMethodId;

    private String expenditurePurposeName;

    private String remark;

}