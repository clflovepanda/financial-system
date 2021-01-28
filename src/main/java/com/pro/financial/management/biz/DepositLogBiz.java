package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.dao.*;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private RevenueDao revenueDao;
    @Autowired
    private ExpenditureDao expenditureDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectDataSourceDao projectDataSourceDao;


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

    /**
     * 押金审批支付后 剩余押金部分转收入
     * @param expenditureId
     */
    public void toRevenue(Integer expenditureId) {
        ExpenditureEntity expenditureEntity = expenditureDao.selectById(expenditureId);
        if (expenditureEntity == null || expenditureEntity.getProjectId() == null) {
            return;
        }
        ProjectEntity projectEntity = projectDao.selectById(expenditureEntity.getProjectId());
        QueryWrapper<ProjectDataSourceEntity> dataSourceEntityQueryWrapper = new QueryWrapper<>();
        dataSourceEntityQueryWrapper.eq("project_id", projectEntity.getProjectId());
        ProjectDataSourceEntity dataso =  projectDataSourceDao.selectOne(dataSourceEntityQueryWrapper);
        QueryWrapper<DepositLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expenditure_id", expenditureId);
        //查出当前支出
        List<DepositLogEntity> depositLogEntities = depositLogDao.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(depositLogEntities)) {
            Integer revenueId = depositLogEntities.get(0).getRevenueId();
            //查出关联的全部支出
            List<DepositLogEntity> depositAll = depositLogDao.getListByRevenueId(revenueId);
            //押金对象
            RevenueEntity deposit = revenueDao.selectById(revenueId);
            if (CollectionUtils.isEmpty(depositAll)) {
                return;
            }
            //是否全部支付
            boolean allPaid = true;
            for (DepositLogEntity depositLogEntity : depositAll) {
                if (depositLogEntity.getState() == 0) {
                    allPaid = false;
                }
            }
            //剩余金额转收入
            if (allPaid) {
                //计算押金金额
                RevenueEntity revenueEntity = revenueDao.getById(revenueId);
                //总押金金额
                BigDecimal depositMoney = revenueEntity.getCnyMoney();
                BigDecimal paidMoney = BigDecimal.ZERO;
                for (DepositLogEntity depositLogEntity : depositAll) {
                    paidMoney = paidMoney.add(depositLogEntity.getExpenditureMoney());
                }
                //如果支付金额小其余的用来做收入
                if (paidMoney.compareTo(depositMoney) == -1) {
                    BigDecimal restMoney = depositMoney.subtract(paidMoney);
                    String revenueNo;
                    String lastNo = revenueDao.selectLastNo();
                    if (StringUtils.isEmpty(lastNo)) {
                        lastNo = "001";
                    }
                    revenueNo = CommonUtil.generatorNO(CommonConst.initials_revenue, "", lastNo);
                    RevenueEntity depositRevenue = new RevenueEntity();
                    depositRevenue.setCreateUser(expenditureEntity.getCreateUser());
                    depositRevenue.setUpdateUser(expenditureEntity.getCreateUser());
                    depositRevenue.setRevenueNo(revenueNo);
                    depositRevenue.setCnyMoney(restMoney);
                    depositRevenue.setProjectId(deposit.getProjectId());
                    depositRevenue.setDataSourceId(deposit.getDataSourceId());
                    //13数据库中为押金转收入
                    depositRevenue.setRevenueTypeId(13);
                    //无到款记录认款记录 设为0
                    depositRevenue.setReceivementId(deposit.getReceivementId());
                    depositRevenue.setSubscriptionLogId(deposit.getId());
                    revenueDao.insert(depositRevenue);
                }
            }
        }
    }

    public List<DepositLogEntity> getListByRevenueIdWithoutState(Integer depositId) {
        return depositLogDao.getListByRevenueIdWithoutState(depositId);
    }

    public int deleteByExpenditureId(Integer id) {
        QueryWrapper<DepositLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("expenditure_id", id);
        DepositLogEntity depositLogEntity = new DepositLogEntity();
        depositLogEntity.setState(-1);
        return depositLogDao.update(depositLogEntity, queryWrapper);
    }

    public List<DepositLogEntity> getByExpenditureId(Integer expenditureId) {
        QueryWrapper<DepositLogEntity>  wrapper = new QueryWrapper();
        wrapper.eq("expenditure_id", expenditureId).eq("state", 1);
        return depositLogDao.selectList(wrapper);
    }

    public List<DepositLogEntity> getAllByExpenditureId(Integer expenditureId) {
        QueryWrapper<DepositLogEntity>  wrapper = new QueryWrapper();
        wrapper.eq("expenditure_id", expenditureId);
        return depositLogDao.selectList(wrapper);
    }
}
