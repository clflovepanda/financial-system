package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.dao.DepositLogDao;
import com.pro.financial.management.dao.entity.DepositLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 押金流程表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Service
public class DepositLogBiz extends ServiceImpl<DepositLogDao, DepositLogEntity> {

    @Autowired
    private DepositLogDao depositLogDao;


    /**
     * 根据收入记录查询对应押金的支出记录
     * @param revenueId
     * @return
     */
    public List<Integer> getDepLog(Integer revenueId) {
        QueryWrapper<DepositLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("expenditure_id").eq("revenue_id", revenueId);
        List<Integer> list = (List)depositLogDao.selectObjs(queryWrapper);
        return list;
    }

    public List<DepositLogEntity> getListByRevenueId(Integer depositId) {
        return depositLogDao.getListByRevenueId(depositId);
    }
}
