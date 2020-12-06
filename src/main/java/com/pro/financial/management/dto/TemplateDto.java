package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class TemplateDto {

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
    private List<TemplateDto> templates;

}
