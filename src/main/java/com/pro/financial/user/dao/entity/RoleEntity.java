package com.pro.financial.user.dao.entity;

import com.pro.financial.user.dto.PermissionDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class RoleEntity {
    private int roleId;
    private String roleName;
    private String isSysRole;
    private String remarks;
    private String state;
    private String taxis;
    private Date createDatetime;
    private String exattribute;
    private List<PermissionEntity> permissions;
}
