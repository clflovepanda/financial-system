package com.pro.financial.user.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 验证码表
 * </p>
 *
 * @author panda
 * @since 2020-12-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("validate")
public class ValidateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 验证码表主键
     */
    @TableId(value = "validate_id", type = IdType.AUTO)
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
    private LocalDateTime createDatetime;


}
