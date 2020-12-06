package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 工时模板表
 * </p>
 *
 * @author panda
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("template")
@Getter
@Setter
@ToString
public class TemplateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板主键id
     */
    @TableId(value = "template_id", type = IdType.AUTO)
    private Integer templateId;

    /**
     * 上一级id，无上一级id为0
     */
    private Integer parentId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 名称
     */
    private String templateName;

    /**
     * 单位工时
     */
    private String takeTime;

    /**
     * 项目系数
     */
    private Integer projectFactor;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 总工时
     */
    private String timeAmount;

    /**
     * 工时单价
     */
    private BigDecimal timePrice;

    /**
     * 费用
     */
    private BigDecimal timePriceAmount;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 职位id
     */
    private Integer positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 最高级的模板id
     */
    private String originId;

    /**
     * 所属模板（第几套）
     */
    private String sets;

    /**
     * 子级别模板
     */
    @TableField(exist = false)
    private List<TemplateEntity> templates;
}
