package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureAuditLogBiz;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.biz.ExportBiz;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.dao.entity.ExpenditureStatisticsEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.RevenueDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private ExportBiz exportBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;
    @Autowired
    private ExpenditureAuditLogBiz expenditureAuditLogBiz;

    @RequestMapping("/deposit")
    public JSONObject deposit(HttpServletRequest request) {
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
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<RevenueDto> revenueDtos = revenueBiz.searchList(projectId, revenueNo, null, receivementTypeId,
                companyId, remitter, createUser, startDate, endDate, projectName, projectNo, revenueTypeId, null, null);
        BigDecimal toBeReturned = new BigDecimal(0);
        BigDecimal returned = new BigDecimal(0);
        BigDecimal returning = new BigDecimal(0);
        for (RevenueDto revenueDto : revenueDtos) {
            toBeReturned = toBeReturned.add(revenueDto.getToBeReturned() == null ? new BigDecimal(0) : revenueDto.getToBeReturned());
            returned = returned.add(revenueDto.getReturned() == null ? new BigDecimal(0) : revenueDto.getReturned());
            returning = returning.add(revenueDto.getReturning() == null ? new BigDecimal(0) : revenueDto.getReturning());
        }

        return exportBiz.exportDepositCSV(revenueDtos);
    }

    @RequestMapping("/expenditure")
    public JSONObject expenditure(HttpServletRequest request) {
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
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");

        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ExpenditureDto> expenditureDtos = expenditureBiz.searchList(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord, projectName, projectNo,
                null, null);
        for (ExpenditureDto expenditureDto : expenditureDtos) {
            expenditureDto.setAuditType(expenditureAuditLogBiz.getLastLog(expenditureDto.getExpenditureId()));
            expenditureDto.setExpenditureAuditLogs(expenditureAuditLogBiz.getLogByEId(expenditureDto.getExpenditureId()));
        }
        ExpenditureStatisticsEntity expenditureStatisticsEntity = expenditureBiz.getStatistics(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord, projectName, projectNo);
        return exportBiz.exportExpenditureCSV(expenditureDtos);
    }
}
