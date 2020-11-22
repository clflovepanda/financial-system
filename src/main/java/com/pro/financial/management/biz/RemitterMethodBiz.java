package com.pro.financial.management.biz;

import com.pro.financial.management.converter.RemitterMethodDto2Entity;
import com.pro.financial.management.dao.RemitterMethodDao;
import com.pro.financial.management.dto.RemitterMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemitterMethodBiz {

    @Autowired
    private RemitterMethodDao remitterMethodDao;

    public int addRemitterMethod(RemitterMethodDto remitterMethodDto) {
        return remitterMethodDao.insert(RemitterMethodDto2Entity.instance.convert(remitterMethodDto));
    }
}
