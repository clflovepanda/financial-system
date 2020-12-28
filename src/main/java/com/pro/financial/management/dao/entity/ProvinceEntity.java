package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("province")
@Getter
@Setter
@ToString
public class ProvinceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "province_id", type = IdType.AUTO)
    private Integer provinceId;

    private String name;

    private String code;

    private String country;

    @TableField(exist = false)
    private List<CityEntity> city;


}
