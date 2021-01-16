package com.pro.financial.management.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.converter.SubscriptionLogDto2Entity;
import com.pro.financial.management.converter.SubscriptionLogEntity2Dto;
import com.pro.financial.management.dao.ReceivementDao;
import com.pro.financial.management.dao.RevenueDao;
import com.pro.financial.management.dao.SubscriptionLogDao;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.RevenueEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public JSONObject delSublog(Integer id) {
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
            result.put("code", 0);
            result.put("msg", "未查询到关联收入或押金");
            return result;
        }
        ReceivementEntity receivementEntity = receivementDao.getById(subscriptionLogEntity.getReceivementId());
        if (receivementEntity.getState() > 3) {
            result.put("code", 0);
            result.put("msg", "该认款已经做账,无法删除");
            return result;
        }
        subscriptionLogEntity.setState(0);
        //删除认款记录
        subscriptionLogDao.updateById(subscriptionLogEntity);
        //删除收入
        RevenueEntity revenueEntity = new RevenueEntity();
        revenueEntity.setDelete(0);
        revenueDao.update(revenueEntity, queryWrapper);
        //修改到款状态

        BigDecimal updateMonye = this.gethadSubscriptionTotalMoneyByRId(receivementEntity.getId());
        if (updateMonye == null || updateMonye.compareTo(new BigDecimal(0)) == 0 ) {
            receivementEntity.setState(0);
        }
        if (updateMonye != null && receivementEntity.getReceivementMoney().compareTo(updateMonye) == 1 ) {
            receivementDao.updatestate(receivementEntity.getId(), 2);
        }

        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
