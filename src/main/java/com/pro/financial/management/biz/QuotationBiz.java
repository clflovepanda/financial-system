package com.pro.financial.management.biz;

import com.pro.financial.management.converter.QuotationDto2Entity;
import com.pro.financial.management.converter.QuotationEntity2Dto;
import com.pro.financial.management.dao.QuotationDao;
import com.pro.financial.management.dao.entity.QuotationEntity;
import com.pro.financial.management.dto.QuotationDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotationBiz {

    @Autowired
    private QuotationDao quotationDao;

    public List<QuotationDto> getQuotation(String quotationName, String quotationNo, Integer limit, Integer offset) {
        List<QuotationEntity> quotationEntities = quotationDao.getQuotation(quotationName, quotationNo, limit, offset);
        return ConvertUtil.convert(QuotationEntity2Dto.instance, quotationEntities);
    }

    public int addQuotation(QuotationDto quotationDto) {
        QuotationEntity quotationEntity = QuotationDto2Entity.instance.convert(quotationDto);
        return quotationDao.addQuotation(quotationEntity);
    }

    public QuotationDto getByQuotationId(int quotationId) {
        QuotationEntity quotationEntity = quotationDao.selectById(quotationId);
        return QuotationEntity2Dto.instance.convert(quotationEntity);
    }

    public int updateQuotation(QuotationDto quotationDto) {
        QuotationEntity quotationEntity = QuotationDto2Entity.instance.convert(quotationDto);
        return quotationDao.updateQuotationById(quotationEntity);
    }

    public int deleteQuotationTrue(int quotationId) {
        return quotationDao.deleteQuotation(quotationId);
    }
    public int deleteQuotation(int quotationId) {
        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity.setQuotationId(quotationId);
        quotationEntity.setState("0");
        return quotationDao.updateQuotationById(quotationEntity);
    }
}
