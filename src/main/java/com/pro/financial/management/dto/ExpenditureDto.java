package com.pro.financial.management.dto;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class ExpenditureDto {
    private Integer id;

    private String numbering;

    private Integer expenditureType;

    private Integer companyId;

    private Integer projectId;

    private Integer expenditureMethodId;

    private Integer expenditureTypeId;

    private Integer expenditurePurposeId;

    private String expenditurePurposeContent;

    private BigDecimal expenditureMoney;

    private Integer state;

    private String remark;

    private String beneficiaryUnit;

    private String beneficiaryNumber;

    private Integer province;

    private Integer city;

    private String beneficiaryBank;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    private Integer isEffective;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering == null ? null : numbering.trim();
    }

    public Integer getExpenditureType() {
        return expenditureType;
    }

    public void setExpenditureType(Integer expenditureType) {
        this.expenditureType = expenditureType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getExpenditureMethodId() {
        return expenditureMethodId;
    }

    public void setExpenditureMethodId(Integer expenditureMethodId) {
        this.expenditureMethodId = expenditureMethodId;
    }

    public Integer getExpenditureTypeId() {
        return expenditureTypeId;
    }

    public void setExpenditureTypeId(Integer expenditureTypeId) {
        this.expenditureTypeId = expenditureTypeId;
    }

    public Integer getExpenditurePurposeId() {
        return expenditurePurposeId;
    }

    public void setExpenditurePurposeId(Integer expenditurePurposeId) {
        this.expenditurePurposeId = expenditurePurposeId;
    }

    public String getExpenditurePurposeContent() {
        return expenditurePurposeContent;
    }

    public void setExpenditurePurposeContent(String expenditurePurposeContent) {
        this.expenditurePurposeContent = expenditurePurposeContent == null ? null : expenditurePurposeContent.trim();
    }

    public BigDecimal getExpenditureMoney() {
        return expenditureMoney;
    }

    public void setExpenditureMoney(BigDecimal expenditureMoney) {
        this.expenditureMoney = expenditureMoney;
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

    public String getBeneficiaryUnit() {
        return beneficiaryUnit;
    }

    public void setBeneficiaryUnit(String beneficiaryUnit) {
        this.beneficiaryUnit = beneficiaryUnit == null ? null : beneficiaryUnit.trim();
    }

    public String getBeneficiaryNumber() {
        return beneficiaryNumber;
    }

    public void setBeneficiaryNumber(String beneficiaryNumber) {
        this.beneficiaryNumber = beneficiaryNumber == null ? null : beneficiaryNumber.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getBeneficiaryBank() {
        return beneficiaryBank;
    }

    public void setBeneficiaryBank(String beneficiaryBank) {
        this.beneficiaryBank = beneficiaryBank == null ? null : beneficiaryBank.trim();
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

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }
}