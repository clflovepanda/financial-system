package com.pro.financial.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.financial.management.dao.entity.TemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 工时模板表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-11-23
 */
@Repository
public interface TemplateDao extends BaseMapper<TemplateEntity> {

}
