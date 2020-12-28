package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.financial.user.dao.entity.DataSourceEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("expenditure")
public class ExpenditureEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "expenditure_id", type = IdType.AUTO)
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
    private String name;
}