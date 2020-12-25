package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.converter.ReceivableDto2Entity;
import com.pro.financial.management.converter.ReceivableEntity2Dto;
import com.pro.financial.management.dao.ReceivableDao;
import com.pro.financial.management.dao.entity.ReceivableEntity;
import com.pro.financial.management.dto.ReceivableDto;
import com.pro.financial.utils.CommonUtil;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 应收单表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-11-21
 */
@Service
public class ReceivableBiz extends ServiceImpl<ReceivableDao, ReceivableEntity> {

    @Autowired
    private ReceivableDao receivableDao;

    public List<ReceivableDto> getReceivable(String org, String receivableNo, String taxableServiceName, String userName, Date startDate, Date endDate, Integer limit, Integer offset) {

        List<ReceivableEntity> receivableEntities = receivableDao.getReceivable(org, receivableNo, taxableServiceName, userName, startDate, endDate, limit, offset);
        return ConvertUtil.convert(ReceivableEntity2Dto.instance, receivableEntities);
    }

    public int addReceivable(ReceivableDto receivableDto) {
        ReceivableEntity receivableEntity = ReceivableDto2Entity.instance.convert(receivableDto);
        //生成编号
        String receivableNo;
        //获取最后一条数据的编号
        String lastNo = receivableDao.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            lastNo = "001";
        }
        receivableNo = CommonUtil.generatorNO(CommonConst.initials_receivable, receivableDto.getDataSource(), lastNo);
        receivableEntity.setReceivableNo(receivableNo);
        return receivableDao.insert(receivableEntity);
    }
    public int updateReceivable(ReceivableDto receivableDto) {
        ReceivableEntity receivableEntity = ReceivableDto2Entity.instance.convert(receivableDto);
        return receivableDao.updateById(receivableEntity);
    }
    public int delReceivable(int receivableId) {
        ReceivableEntity receivableEntity = new ReceivableEntity();
        receivableEntity.setState("0");
        return receivableDao.updateById(receivableEntity);
    }

    public List<ReceivableEntity> getListByProjectIds(List<Integer> projectIds) {
        return receivableDao.getListByProjectIds(projectIds);
    }
}
