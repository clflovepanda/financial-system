package com.pro.financial.management.dto;

import lombok.ToString;

import java.util.Date;

@ToString
public class AccountingLogDto {
    private Integer id;

    private Integer receivementId;

    private String voucherNo;

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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
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