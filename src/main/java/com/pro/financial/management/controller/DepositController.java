package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.biz.DepositLogBiz;
import com.pro.financial.management.biz.ExpenditureAuditLogBiz;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.dao.DepositLogDao;
import com.pro.financial.management.dao.entity.DepositLogEntity;
import com.pro.financial.management.dao.entity.ExpenditureAuditLogEntity;
import com.pro.financial.management.dao.entity.ExpenditureEntity;
import com.pro.financial.management.dto.ExpenditureAuditLogDto;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.user.biz.UserBiz;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private DepositLogBiz depositLogBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;
    @Autowired
    private ExpenditureAuditLogBiz expenditureAuditLogBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String projectId = request.getParameter("projectId");
        //押金编号
        String revenueNo = request.getParameter("revenueNo");
        //项目名称
        String projectName = request.getParameter("projectName");
        //项目编号
        String projectNo = request.getParameter("projectNo");
        //到款账户
        String companyId = request.getParameter("companyId");
        //到款种类
        String receivementTypeId = request.getParameter("receivementTypeId");
        //汇款方
        String remitter = request.getParameter("remitter");
        //认款人
        String createUser = request.getParameter("createUser");
        String revenueTypeId = "";

        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit*(offset - 1);
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<RevenueDto> revenueDtos = revenueBiz.searchList(projectId, revenueNo, null, receivementTypeId,
                companyId, remitter, createUser, startDate, endDate, projectName, projectNo, revenueTypeId, limit, offset);
        int count = revenueBiz.getCount(projectId, revenueNo, null, receivementTypeId,
                companyId, remitter, createUser, startDate, endDate, projectName, projectNo, revenueTypeId);
        Map<String, Object> resutlMap = new HashMap<>();
        resutlMap.put("deposit", revenueDtos);
        resutlMap.put("count", count);
        BigDecimal toBeReturned = new BigDecimal(0);
        BigDecimal returned = new BigDecimal(0);
        BigDecimal returning = new BigDecimal(0);
        for (RevenueDto revenueDto : revenueDtos) {
            toBeReturned = toBeReturned.add(revenueDto.getToBeReturned() == null ? new BigDecimal(0) : revenueDto.getToBeReturned());
            returned = returned.add(revenueDto.getReturned() == null ? new BigDecimal(0) : revenueDto.getReturned());
            returning = returning.add(revenueDto.getReturning() == null ? new BigDecimal(0) : revenueDto.getReturning());
        }
        resutlMap.put("toBeReturned", toBeReturned);
        resutlMap.put("returned", returned);
        resutlMap.put("returning", returning);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resutlMap);
        return result;
    }

    @RequestMapping("/detail")
    public JSONObject detail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Map<String, Object> resultMap = new HashMap<>();;
        Integer revenueId = Integer.parseInt(StringUtils.isEmpty(request.getParameter("revenueId")) ? "0" : request.getParameter("revenueId"));
        if (revenueId < 1) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        RevenueDto revenueDto =  revenueBiz.getByRevenueId(revenueId);
        List<Integer> expenditureIds =  depositLogBiz.getDepLog(revenueId);
        List<ExpenditureEntity> expenditureEntities = expenditureBiz.selectListByIds(expenditureIds);

        resultMap.put("revenue", revenueDto);
        resultMap.put("expenditure", expenditureEntities);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resultMap);
        return result;
    }


    @RequestMapping("/del")
    public JSONObject revenue(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        Integer expenditureId = Integer.parseInt(StringUtils.isEmpty(request.getParameter("expenditureId")) ? "0" : request.getParameter("expenditureId"));
        if (expenditureId < 1) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        ExpenditureEntity expenditureEntity = expenditureBiz.selectById(expenditureId);
        if (expenditureEntity.getCreateUser() - userId != 0) {
            result.put("code", 1001);
            result.put("msg", "非本人无法删除");
            return result;
        }
        if (expenditureEntity.getState() - CommonConst.expenditure_audit_type_paid == 0) {
            result.put("code", 1001);
            result.put("msg", "已经支付无法删除");
            return result;
        }
        List<ExpenditureAuditLogDto> expenditureAuditLogDtos = expenditureAuditLogBiz.getLogByEId(expenditureId);
        if (CollectionUtils.isEmpty(expenditureAuditLogDtos)) {
            expenditureBiz.deleteExpenditureByid(expenditureId);
            QueryWrapper<DepositLogEntity> wrapper  = new QueryWrapper<>();
            wrapper.eq("expenditure", expenditureId);
            depositLogBiz.remove(wrapper);
        } else {
            if (expenditureAuditLogDtos.get(0).getAuditType() - CommonConst.expenditure_audit_type_paid == 0) {
                result.put("code", 1001);
                result.put("msg", "已经支付无法删除");
                return result;
            }
            expenditureBiz.deleteExpenditureByid(expenditureId);
            //删除支付审批流程
            expenditureAuditLogBiz.deleteExpenditureByid(expenditureId);
            //删除押金流程
            QueryWrapper<DepositLogEntity> wrapper  = new QueryWrapper<>();
            wrapper.eq("expenditure", expenditureId);
            depositLogBiz.remove(wrapper);
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
