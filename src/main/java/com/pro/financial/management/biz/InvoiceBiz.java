package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.InvoiceEntity2Dto;
import com.pro.financial.management.dao.InvoiceDao;
import com.pro.financial.management.dao.entity.InvoiceEntity;
import com.pro.financial.management.dto.InvoiceDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 发票应收表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-12-27
 */
@Service
public class InvoiceBiz extends ServiceImpl<InvoiceDao, InvoiceEntity> {

    @Autowired
    private InvoiceDao invoiceDao;

    public List<InvoiceDto> getList(String keyWord, String username, Date startDate, Date endDate) {
        List<InvoiceEntity> invoiceEntities = invoiceDao.getList(keyWord, username, startDate, endDate);
        return ConvertUtil.convert(InvoiceEntity2Dto.instance, invoiceEntities);
    }

    public String exprotInvoice(List<InvoiceDto> invoiceDtos) {
        //todo
        if (CollectionUtils.isEmpty(invoiceDtos)) {
            return "";
        }
        return "";
    }

    public String selectLastNo() {
        return invoiceDao.selectLastNo();
    }
}
