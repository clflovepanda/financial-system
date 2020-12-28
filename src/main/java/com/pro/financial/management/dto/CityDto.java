package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CityDto {

    private Integer cityId;

    private String cityName;

    private String cityCode;

    private Integer provinceId;
}
