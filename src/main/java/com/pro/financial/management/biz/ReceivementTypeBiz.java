package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ReceivementTypeDto2Entity;
import com.pro.financial.management.dao.ReceivementTypeDao;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dto.ReceivementTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivementTypeBiz {

    @Autowired
    private ReceivementTypeDao receivementTypeDao;

    public int addReceivementType(ReceivementTypeDto receivementTypeDto) {
        return receivementTypeDao.insert(ReceivementTypeDto2Entity.instance.convert(receivementTypeDto));
    }

    public List<ReceivementTypeEntity> getList() {
        return receivementTypeDao.getList();
    }
}
