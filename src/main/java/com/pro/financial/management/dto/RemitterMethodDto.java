package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RemitterMethodDto {
    private Integer remitterMethodId;

    private String remitterMethodName;

    private String remark;

}