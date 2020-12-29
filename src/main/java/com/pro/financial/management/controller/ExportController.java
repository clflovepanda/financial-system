package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.biz.InvoiceBiz;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.dto.InvoiceDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private InvoiceBiz invoiceBiz;
    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;

    @RequestMapping("/invoice")
    public JSONObject exportInvoice(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWord = request.getParameter("keyWord");
        String username = request.getParameter("username");
        //项目时间
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<InvoiceDto> invoiceDtos = invoiceBiz.getList(keyWord, username, startDate, endDate);
        String url = invoiceBiz.exprotInvoice(invoiceDtos);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", url);
        return result;
    }
}
