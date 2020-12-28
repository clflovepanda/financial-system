package com.pro.financial.management.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("city")
public class CityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "city_id", type = IdType.AUTO)
    private Integer cityId;

    private String cityName;

    private String cityCode;

    private Integer provinceId;


}
