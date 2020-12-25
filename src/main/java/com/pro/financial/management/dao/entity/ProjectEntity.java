package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project")
@Getter
@Setter
@ToString
public class ProjectEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    private String code;

    private String name;

    private String fullname;

    private Date startDate;

    private Date endDate;

    private BigDecimal estincome;

    private BigDecimal budget;

    private String description;

    private Integer state;

    private Integer auditingState;

    private Integer saleCommisState;

    private Integer settlementState;

    private Integer createUser;

    private Date ctime;

    private Integer updateUser;

    private Date utime;

    //项目经理
    @TableField(exist = false)
    private String managerName;

    //销售经理
    @TableField(exist = false)
    private String salesName;

    //项目成员
    @TableField(exist = false)
    private String userNames;

    //项目成员
    @TableField(exist = false)
    private String dataSourceName;

    //项目公司
    @TableField(exist = false)
    private String company;

    //项目公司
    @TableField(exist = false)
    private String createUserName;

}