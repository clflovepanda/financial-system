package com.pro.financial.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ValidateDto {
    private Integer validateId;

    /**
     * 验证码code
     */
    private String validateCode;

    /**
     * 验证码地址
     */
    private String validateUrl;

    /**
     * 创建时间
     */
    private Date createDatetime;
}
