package com.pro.financial.management.dao.entity;

import lombok.ToString;

import java.util.Date;

@ToString
public class ExpenditureProjectEntity {
    private Integer id;

    private Integer expenditureId;

    private Integer projectId;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(Integer expenditureId) {
        this.expenditureId = expenditureId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}