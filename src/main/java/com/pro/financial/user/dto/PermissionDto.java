package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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

    private List<PermissionDto> permissionSons;
}
