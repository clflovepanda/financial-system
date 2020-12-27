package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.pro.financial.user.dao.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 工时记录表
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_task")
public class ProjectTaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    /**
     * 项目id
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

    @TableField(exist = false)
    private Integer projectId;

    @TableField(exist = false)
    private Integer relationTemplateId;

    @TableField(exist = false)
    private UserEntity user;


}
