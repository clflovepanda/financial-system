package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.converter.SubscriptionLogDto2Entity;
import com.pro.financial.management.converter.SubscriptionLogEntity2Dto;
import com.pro.financial.management.dao.ReceivementDao;
import com.pro.financial.management.dao.RevenueDao;
import com.pro.financial.management.dao.SubscriptionLogDao;
import com.pro.financial.management.dao.entity.DepositLogEntity;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionLogBiz {

    @Autowired
    private SubscriptionLogDao subscriptionLogDao;
    @Autowired
    private RevenueDao revenueDao;
    @Autowired
    private ReceivementDao receivementDao;
    @Autowired
    private DepositLogBiz depositLogBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;

    public int addSubscriptionLog(SubscriptionLogDto subscriptionLogDto) {
        SubscriptionLogEntity subscriptionLogEntity = SubscriptionLogDto2Entity.instance.convert(subscriptionLogDto);
        int count = subscriptionLogDao.insert(subscriptionLogEntity);
        subscriptionLogDto.setId(subscriptionLogEntity.getId());
        return count;
    }

    public List<SubscriptionLogEntity> getListByReceivementIds(List<Integer> receivementIds) {
        if (CollectionUtils.isEmpty(receivementIds)) {
            return new ArrayList<>();
        }
        return subscriptionLogDao.getListByReceivementIds(receivementIds);
    }

    public List<SubscriptionLogEntity> getListByReceivementIdsnew(List<Integer> receivementIds, String projectName, String dataSourceId, String revenueTypeId) {
        if (CollectionUtils.isEmpty(receivementIds)) {
            return new ArrayList<>();
        }
        return subscriptionLogDao.getListByReceivementIdsnew(receivementIds, projectName, dataSourceId, revenueTypeId);
    }

    public List<SubscriptionLogEntity> getListByProjectIds(List<Integer> projectIds) {
        return subscriptionLogDao.getListByProjectIds(projectIds);
    }

    public List<SubscriptionLogDto> getListByReceivementId(Integer receivementId) {
        return ConvertUtil.convert(SubscriptionLogEntity2Dto.instance, subscriptionLogDao.getListByProjectId(receivementId));
    }


    public int deleteByReceivementId(Integer id) {
        return subscriptionLogDao.deleteByReceivementId(id);
    }

    /**
     * 获取剩余认款金额
     * @param id
     * @return
     */
    public BigDecimal gethadSubscriptionTotalMoneyByRId(Integer id) {
        return subscriptionLogDao.gethadSubscriptionTotalMoneyByRId(id);
    }

    @Transactional
    public JSONObject delSublog(Integer id, List<RevenueEntity> deposit) {
        JSONObject result = new JSONObject();
        SubscriptionLogEntity subscriptionLogEntity = subscriptionLogDao.selectById(id);
        if (subscriptionLogEntity == null || subscriptionLogEntity.getState() == 0) {
            result.put("code", 1002);
            result.put("msg", "已经删除该纪录");
            return result;
        }
        QueryWrapper<RevenueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("subscription_log_id", id);
        List<RevenueEntity> revenueEntities = revenueDao.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(revenueEntities)) {
            result.put("code", 1003);
            result.put("msg", "未查询到关联收入或押金");
            return result;
        }
        ReceivementEntity receivementEntity = receivementDao.getById(subscriptionLogEntity.getReceivementId());
        if (receivementEntity.getState() > 3) {
            result.put("code", 1004);
            result.put("msg", "该认款已经做账,无法删除");
            return result;
        }

        //如果有押金 删除押金的支出等记录
        if (!CollectionUtils.isEmpty(deposit)) {
            for (RevenueEntity revenueEntity : deposit) {
                List<DepositLogEntity> depositLogEntities = depositLogBiz.getListByRevenueIdWithoutState(revenueEntity.getId());
                if (!CollectionUtils.isEmpty(depositLogEntities)) {
                    for (DepositLogEntity depositLogEntity : depositLogEntities) {
                        depositLogEntity.setState(0);
                        //修改押金记录表状态
                        depositLogBiz.updateById(depositLogEntity);
                        //修改支出表状态
                        expenditureBiz.deleteExpenditureByid(depositLogEntity.getExpenditureId());
                    }
                }
            }
        }
        subscriptionLogEntity.setState(0);
        //删除认款记录
        subscriptionLogDao.updateById(subscriptionLogEntity);
        //删除收入
        revenueDao.deleteBySubLogId(id);

        //修改到款状态

        BigDecimal updateMonye = this.gethadSubscriptionTotalMoneyByRId(receivementEntity.getId());
        if (updateMonye == null || updateMonye.compareTo(new BigDecimal(0)) == 0 ) {
            receivementDao.updatestate(receivementEntity.getId(), 0);
        }
        if (updateMonye != null && receivementEntity.getReceivementMoney().compareTo(updateMonye) == 1 ) {
            receivementDao.updatestate(receivementEntity.getId(), 2);
        }

        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
