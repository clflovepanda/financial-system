package com.pro.financial.user.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserEntity {
    private Integer userId;
    private Integer depId;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private Integer state;
    private Date registerTime;
    private Date createDatetime;
    private List<RoleEntity> roles;
}
