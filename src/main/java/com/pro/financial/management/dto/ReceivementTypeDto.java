package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReceivementTypeDto {
    private Integer receivementTypeId;

    private String receivementTypeName;

    private String remark;

}