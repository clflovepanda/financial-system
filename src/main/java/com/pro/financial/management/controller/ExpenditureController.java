package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.dto.ExpenditureDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/expenditure")
public class ExpenditureController {

    @Autowired
    private ExpenditureBiz expenditureBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditure(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
        int count = expenditureBiz.addExpenditure(expenditureDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/statistics")
    public JSONObject statistics(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //属性
        String attribute = request.getParameter("attribute");
        String company = request.getParameter("company");
        String projectNo = request.getParameter("projectNo");
        String applyUser = request.getParameter("applyUser");
        //用途
        String purpose = request.getParameter("purpose");
        String state = request.getParameter("state");
        //收款单位
        String beneficiaryUnit = request.getParameter("beneficiaryUnit");
        String startDt = request.getParameter("startDt");
        String entDt = request.getParameter("entDt");
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startDt) && StringUtils.isNotEmpty(entDt)) {
            startDate = new Date(Long.parseLong(startDt));
            endDate = new Date(Long.parseLong(entDt));
        }

        List<ExpenditureDto> expenditureDtos = expenditureBiz.statistics(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit, startDate, endDate);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", expenditureDtos);
        return result;
    }
}
