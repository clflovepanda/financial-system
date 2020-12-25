package com.pro.financial.management.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RemitterMethodEntity {
    private Integer remitterMethodId;

    private String remitterMethodName;

    private String remark;
}