package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目所选模板表
 * </p>
 *
 * @author panda
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("project_task_relation")
public class ProjectTaskRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "task_relation_id", type = IdType.AUTO)
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
