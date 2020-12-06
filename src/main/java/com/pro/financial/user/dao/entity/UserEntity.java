package com.pro.financial.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class UserEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
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
