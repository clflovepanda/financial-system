package com.pro.financial.management.dao;

import com.pro.financial.management.dao.entity.DepositLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 押金流程表 Mapper 接口
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Repository
public interface DepositLogDao extends BaseMapper<DepositLogEntity> {

    List<DepositLogEntity> getListByRevenueId(@Param("depositId") Integer depositId);
}
