package com.pro.financial.management.biz;

import com.pro.financial.management.converter.ReceivementDto2Entity;
import com.pro.financial.management.dao.ReceivementDao;
import com.pro.financial.management.dto.ReceivementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivementBiz {

    @Autowired
    private ReceivementDao receivementDao;

    public int addReceivement(ReceivementDto receivementDto) {
        return receivementDao.insert(ReceivementDto2Entity.instance.convert(receivementDto));
    }
}
