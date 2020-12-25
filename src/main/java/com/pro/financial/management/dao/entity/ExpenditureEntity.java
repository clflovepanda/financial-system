package com.pro.financial.management.dao.entity;

import com.pro.financial.user.dao.entity.DataSourceEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
public class ExpenditureEntity {
    private Integer expenditureId;

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

    private ProjectEntity project;
    private DataSourceEntity dataSource;
    private ExpenditureMethodEntity expenditureMethod;
    private ExpenditureTypeEntity expenditureTypeEntity;
    private ExpenditurePurposeEntity expenditurePurpose;

    private String username;
    private String expenditureMethodName;
    private String expenditureTypeName;
    private String expenditurePurposeName;
    private String coName;
}