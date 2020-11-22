package com.pro.financial.management.dto;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class ReceivementDto {
    private Integer id;

    private Integer companyId;

    private Integer receivementTypeId;

    private BigDecimal receivementMoney;

    private String remitterMethodId;

    private String remitter;

    private Date receiveDate;

    private Integer state;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getReceivementTypeId() {
        return receivementTypeId;
    }

    public void setReceivementTypeId(Integer receivementTypeId) {
        this.receivementTypeId = receivementTypeId;
    }

    public BigDecimal getReceivementMoney() {
        return receivementMoney;
    }

    public void setReceivementMoney(BigDecimal receivementMoney) {
        this.receivementMoney = receivementMoney;
    }

    public String getRemitterMethodId() {
        return remitterMethodId;
    }

    public void setRemitterMethodId(String remitterMethodId) {
        this.remitterMethodId = remitterMethodId == null ? null : remitterMethodId.trim();
    }

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter == null ? null : remitter.trim();
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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