package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.converter.ProvinceEntity2Dto;
import com.pro.financial.management.dao.ProvinceDao;
import com.pro.financial.management.dao.entity.ProvinceEntity;
import com.pro.financial.management.dto.ProvinceDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-12-28
 */
@Service
public class ProvinceBiz extends ServiceImpl<ProvinceDao, ProvinceEntity> {

    @Autowired
    private ProvinceDao provinceDao;

    public List<ProvinceDto> getCity() {
        List<ProvinceEntity> provinceEntities = provinceDao.getCity();
        return ConvertUtil.convert(ProvinceEntity2Dto.instance, provinceEntities);
    }
}
