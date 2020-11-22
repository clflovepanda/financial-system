package com.pro.financial.management.dto;

import lombok.ToString;

@ToString
public class ExpenditureTypeDto {
    private Integer id;

    private Integer expenditureMethodId;

    private String name;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpenditureMethodId() {
        return expenditureMethodId;
    }

    public void setExpenditureMethodId(Integer expenditureMethodId) {
        this.expenditureMethodId = expenditureMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}