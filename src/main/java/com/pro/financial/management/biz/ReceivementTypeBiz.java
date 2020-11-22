package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ReceivementTypeDto2Entity;
import com.pro.financial.management.dao.ReceivementTypeDao;
import com.pro.financial.management.dto.ReceivementTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivementTypeBiz {

    @Autowired
    private ReceivementTypeDao receivementTypeDao;

    public int addReceivementType(ReceivementTypeDto receivementTypeDto) {
        return receivementTypeDao.insert(ReceivementTypeDto2Entity.instance.convert(receivementTypeDto));
    }
}
