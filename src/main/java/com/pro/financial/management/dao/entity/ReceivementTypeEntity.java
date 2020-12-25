package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReceivementTypeEntity {
    private Integer receivementTypeId;

    private String receivementTypeName;

    private String remark;
}