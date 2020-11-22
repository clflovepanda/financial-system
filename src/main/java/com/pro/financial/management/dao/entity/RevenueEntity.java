package com.pro.financial.management.dao.entity;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class RevenueEntity {
    private Integer id;

    private Integer receivementId;

    private Integer revenueTypeId;

    private Integer dataSourceId;

    private BigDecimal cnyMoney;

    private Integer delete;

    private String remark;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceivementId() {
        return receivementId;
    }

    public void setReceivementId(Integer receivementId) {
        this.receivementId = receivementId;
    }

    public Integer getRevenueTypeId() {
        return revenueTypeId;
    }

    public void setRevenueTypeId(Integer revenueTypeId) {
        this.revenueTypeId = revenueTypeId;
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public BigDecimal getCnyMoney() {
        return cnyMoney;
    }

    public void setCnyMoney(BigDecimal cnyMoney) {
        this.cnyMoney = cnyMoney;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}