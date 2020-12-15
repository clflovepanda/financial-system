package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExpenditurePurposeEntity {
    private Integer expenditurePurposeId;

    private Integer expenditureMethodId;

    private String expenditurePurposeName;

    private String remark;

}