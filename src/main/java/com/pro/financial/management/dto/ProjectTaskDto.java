package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProjectTaskDto {

    private Integer taskId;

    /**
     * 项目工时关系表id
     */
    private Integer taskRelationId;

    /**
     * 模板id
     */
    private Integer templateId;

    /**
     * 人员id
     */
    private Integer userId;

    /**
     * 任务状态，1审核通过启用。0审核中或无效
     */
    private String status;

    /**
     * 任务状态，00：初始化，01分配完成，02项目经理确认
     */
    private String taskStatus;

    /**
     * 数量
     */
    private String amount;

    /**
     * 单位工时
     */
    private String takeTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 完成时间
     */
    private LocalDateTime completionTime;

    private Integer projectId;
    private Integer limit;
    private Integer offset;
    private Integer count;
}
