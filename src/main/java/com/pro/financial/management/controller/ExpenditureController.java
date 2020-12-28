package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.converter.ExpenditurePurposeEntity2Dto;
import com.pro.financial.management.converter.ExpenditureTypeEntity2Dto;
import com.pro.financial.management.dao.entity.DepositLogEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.ExpenditurePurposeDto;
import com.pro.financial.management.dto.ExpenditureTypeDto;
import com.pro.financial.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenditure")
public class ExpenditureController {

    @Autowired
    private ExpenditureBiz expenditureBiz;
    @Autowired
    private ExpenditureAuditLogBiz expenditureAuditLogBiz;

    @Autowired
    private ExpenditureTypeBiz expenditureTypeBiz;
    @Autowired
    private ExpenditurePurposeBiz expenditurePurposeBiz;

    @Autowired
    private DepositLogBiz depositLogBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditure(@RequestBody JSONObject jsonInfo, HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
        expenditureDto.setCreateUser(userId);
        expenditureDto.setCtime(new Date());
        expenditureDto.setUpdateUser(userId);
        expenditureDto.setUtime(new Date());
        int count = expenditureBiz.addExpenditure(expenditureDto);
        if (StringUtils.equals("deposit", request.getParameter("flag"))) {
            DepositLogEntity depositLogEntity = new DepositLogEntity();
            depositLogEntity.setExpenditureId(expenditureDto.getExpenditureId());
            depositLogEntity.setRevenueId(jsonInfo.getInteger("revenueId"));
            depositLogBiz.save(depositLogEntity);
        }
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateExpenditure(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
        int count = expenditureBiz.updateExpenditure(expenditureDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getExpenditure(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String projectId = request.getParameter("projectId");
        String companyId = request.getParameter("companyId");
        String numbering = request.getParameter("numbering");
        //支出方式
        String expenditureMethodId = request.getParameter("expenditureMethodId");
        //支出类型
        String expenditureTypeId = request.getParameter("expenditureTypeId");
        //收款人单位
        String beneficiaryUnit = request.getParameter("beneficiaryUnit");
        //申请人
        String createUser = request.getParameter("createUser");
        //最新状态
        String state = request.getParameter("state");
        //工作流
        String expenditureAuditLog = request.getParameter("expenditureAuditLog");
        //用途
        String expenditurePurposeId = request.getParameter("expenditurePurposeId");
        //关键字
        String keyWord = request.getParameter("keyWord");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ExpenditureDto> expenditureDtos = expenditureBiz.searchList(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord);
        for (ExpenditureDto expenditureDto : expenditureDtos) {
            expenditureDto.setAuditType(expenditureAuditLogBiz.getLastLog(expenditureDto.getExpenditureId()));
            expenditureDto.setExpenditureAuditLogs(expenditureAuditLogBiz.getLogByEId(expenditureDto.getExpenditureId()));
        }

        result.put("code", 0);
        result.put("msg", "");
        result.put("data", expenditureDtos);
        return result;
    }

    @RequestMapping("/gettype")
    public JSONObject getType() {
        JSONObject result = new JSONObject();
        Map<String, Object> resultMap = new HashMap<>();
        List<ExpenditureTypeDto> expenditureTypeDtos = ConvertUtil.convert(ExpenditureTypeEntity2Dto.instance, expenditureTypeBiz.getList());
        List<ExpenditurePurposeDto> expenditurePurposeDtos = ConvertUtil.convert(ExpenditurePurposeEntity2Dto.instance,  expenditurePurposeBiz.getList());
        resultMap.put("type", expenditureTypeDtos);
        resultMap.put("purpose", expenditurePurposeDtos);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resultMap);
        return result;
    }
}
