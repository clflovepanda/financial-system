package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.QuotationBiz;
import com.pro.financial.management.dto.QuotationDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/quotation")
public class QuotationController {

    @Autowired
    private QuotationBiz quotationBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String quotationName = request.getParameter("quotationName");
        String quotationNo = request.getParameter("quotationNo");
        Integer limit = StringUtils.isEmpty(request.getParameter("limit")) ? null : Integer.parseInt(request.getParameter("limit"));
        Integer offset = StringUtils.isEmpty(request.getParameter("offset")) ? null : Integer.parseInt(request.getParameter("offset"));
        List<QuotationDto> quotationDtos = quotationBiz.getQuotation(quotationName, quotationNo, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", quotationDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addQuotation(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        QuotationDto quotationDto = JSONObject.parseObject(jsonInfo.toJSONString(), QuotationDto.class);
        int count = quotationBiz.addQuotation(quotationDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getbyid")
    public JSONObject getQuotation(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int quotationId = 0;
        if (StringUtils.isNumeric(request.getParameter("quotationId"))) {
            quotationId = Integer.parseInt(request.getParameter("quotationId"));
        }
        if (quotationId < 1) {
            result.put("code", 1001);
            result.put("msg", "报价单号有误");
            return result;
        }
        QuotationDto quotationDto = quotationBiz.getByQuotationId(quotationId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", quotationDto);
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateQuotation(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        QuotationDto quotationDto = JSONObject.parseObject(jsonInfo.toJSONString(), QuotationDto.class);
        if (quotationDto.getQuotationId() == null || quotationDto.getQuotationId() < 1) {
            result.put("code", 1001);
            result.put("msg", "报价单号有误");
            return result;
        }
        int count = quotationBiz.updateQuotation(quotationDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delQuotation(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int quotationId = 0;
        if (StringUtils.isNumeric(request.getParameter("quotationId"))) {
            quotationId = Integer.parseInt(request.getParameter("quotationId"));
        }
        if (quotationId < 1) {
            result.put("code", 1001);
            result.put("msg", "报价单号有误");
            return result;
        }
        quotationBiz.deleteQuotation(quotationId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

}
