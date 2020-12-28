package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.ProvinceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Repository
public interface ProvinceDao extends BaseMapper<ProvinceEntity> {

    List<ProvinceEntity> getCity();
}
