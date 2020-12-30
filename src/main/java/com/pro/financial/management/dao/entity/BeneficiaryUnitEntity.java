package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收款单位表
 * </p>
 *
 * @author panda
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("beneficiary_unit")
public class BeneficiaryUnitEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "beneficiary_unit_id", type = IdType.AUTO)
    private Integer beneficiaryUnitId;

    /**
     * 收款单位
     */
    private String beneficiaryUnit;

    /**
     * 收款账号
     */
    private String beneficiaryNumber;

    /**
     * 省份代码
     */
    private Integer province;

    /**
     * 城市代码
     */
    private Integer city;

    /**
     * 收款行
     */
    private String beneficiaryBank;


}
