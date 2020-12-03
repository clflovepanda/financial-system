package com.pro.financial.management.dto;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class SubscriptionLogDto {
    private Integer id;

    private Integer receivementId;

    private Integer revenueTypeId;

    private BigDecimal receivementMoney;

    private Date subscriptionDate;

    private Integer state;

    private String remark;

    private Integer createUser;

    private Date ctime;

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

    public BigDecimal getReceivementMoney() {
        return receivementMoney;
    }

    public void setReceivementMoney(BigDecimal receivementMoney) {
        this.receivementMoney = receivementMoney;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
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
}