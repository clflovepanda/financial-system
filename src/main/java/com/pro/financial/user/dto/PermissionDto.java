package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PermissionDto {
    private Integer permissionId;
    private Integer parentId;
    private String name;
    private String intro;
    private Integer category;
    private String uri;
    private Date createDatetime;
    private String state;
}
