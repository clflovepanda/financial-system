package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pro.financial.user.dao.entity.UserEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_user")
public class ProjectUserEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer projectId;

    private Integer userId;

    private Integer type;

    private Date ctime;

    private String username;

    private String mobile;

    private String email;
}