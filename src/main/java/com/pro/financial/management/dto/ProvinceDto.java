package com.pro.financial.management.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProvinceDto {
    private Integer provinceId;

    private String name;

    private String code;

    private String country;
    private List<CityDto> city;
}
