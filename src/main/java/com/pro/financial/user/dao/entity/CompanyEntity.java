package com.pro.financial.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("company")
public class CompanyEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "company_id", type = IdType.AUTO)
    private Integer CompanyId;
    private String coName;
    private String coCode;
    private String isDefault;
    private String description;
    private String state;
    private String taxis;
    private Date createDatetime;
}
