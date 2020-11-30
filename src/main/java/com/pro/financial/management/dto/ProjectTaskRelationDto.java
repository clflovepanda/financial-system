package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectTaskRelationDto {

    private Integer taskRelationId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 模板flag对应模板表value2
     */
    private Integer templateId;

    /**
     * 名称
     */
    private String templateName;

    /**
     * 第几套模板-对应task表 value3
     */
    private String templateFlag;

}
