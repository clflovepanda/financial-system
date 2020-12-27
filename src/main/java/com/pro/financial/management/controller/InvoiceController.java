package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.biz.InvoiceBiz;
import com.pro.financial.management.converter.InvoiceDto2Entity;
import com.pro.financial.management.dto.InvoiceDto;
import com.pro.financial.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 发票应收表 前端控制器
 * </p>
 *
 * @author panda
 * @since 2020-12-27
 */
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceBiz invoiceBiz;


    @RequestMapping("/list")
    public JSONObject getInvoice(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWord = request.getParameter("keyWord");
        String username = request.getParameter("username");
        //项目时间
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<InvoiceDto> invoiceDtos = invoiceBiz.getList(keyWord, username, startDate, endDate);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", invoiceDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addInvoice(@RequestBody InvoiceDto invoiceDto, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        invoiceDto.setOperator(userId);
        invoiceDto.setCreateDatetime(new Date());
        String invoiceNo = "001";
        //获取最后一条数据的编号
        String lastNo = invoiceBiz.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            lastNo = "001";
        }
        invoiceNo = CommonUtil.generatorNO(CommonConst.initials_invoice, "", lastNo);
        invoiceDto.setInvoiceNo(invoiceNo);
        invoiceBiz.save(InvoiceDto2Entity.instance.convert(invoiceDto));
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateInvoice(@RequestBody InvoiceDto invoiceDto) {
        JSONObject result = new JSONObject();
        invoiceBiz.updateById(InvoiceDto2Entity.instance.convert(invoiceDto));
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delInvoice(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer invoiceId = Integer.valueOf(StringUtils.isEmpty(request.getParameter("invoiceId")) ? "0" : request.getParameter("invoiceId"));
        if (invoiceId < 1) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        invoiceBiz.removeById(invoiceId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/export")
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
