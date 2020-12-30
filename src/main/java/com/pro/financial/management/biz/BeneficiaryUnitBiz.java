package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.dao.BeneficiaryUnitDao;
import com.pro.financial.management.dao.entity.BeneficiaryUnitEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收款单位表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-12-31
 */
@Service
public class BeneficiaryUnitBiz extends ServiceImpl<BeneficiaryUnitDao, BeneficiaryUnitEntity> {

    @Autowired
    private BeneficiaryUnitDao beneficiaryUnitDao;

    public List<BeneficiaryUnitEntity> selectByKeyWords(String keyWords) {
        if (StringUtils.isEmpty(keyWords)) {
            return beneficiaryUnitDao.selectList(new QueryWrapper<BeneficiaryUnitEntity>());
        } else {
            QueryWrapper<BeneficiaryUnitEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("beneficiary_unit", keyWords);
            return beneficiaryUnitDao.selectList(queryWrapper);
        }
    }
}
