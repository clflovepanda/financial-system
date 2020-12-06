package com.pro.financial.user.dao;

import com.pro.financial.user.dao.entity.ValidateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 验证码表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-12-06
 */
@Repository
public interface ValidateDao extends BaseMapper<ValidateEntity> {

}
