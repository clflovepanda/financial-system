package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class RoleDto {
    private Integer roleId;
    private String roleName;
    private String isSysRole;
    private String remark;
    private String state;
    private String taxis;
    private Date createDatetime;
    private List<PermissionDto> permissions;
    private List<DataSourceDto> dataSources;
}