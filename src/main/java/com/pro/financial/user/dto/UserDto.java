package com.pro.financial.user.dto;

import com.pro.financial.user.dao.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDto {
    private Integer userId;
    private Integer depId;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private String state;
    private Date registerTime;
    private Date createDatetime;
    private List<Integer> roleId;
    private String roleName;
    private List<RoleEntity> roles;
    private List<PermissionDto> permissions;
}
