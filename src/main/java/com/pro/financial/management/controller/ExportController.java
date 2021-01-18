package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.converter.ReceivementEntity2Dto;
import com.pro.financial.management.dao.entity.ExpenditureStatisticsEntity;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.utils.ConvertUtil;
import com.pro.financial.utils.DateUtil;
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
    @Autowired
    private ProjectBiz projectBiz;
    @Autowired
    private ReceivementBiz receivementBiz;

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
        BigDecimal toBeReturned = BigDecimal.ZERO;
        BigDecimal returned = BigDecimal.ZERO;
        BigDecimal returning = BigDecimal.ZERO;
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

    /**
     * 支出统计
     * @param request
     * @return
     */
    @RequestMapping("/statistics/expenditure")
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
        List<ExpenditureDto> expenditureDtos = expenditureBiz.statistics(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit, startDate, endDate, null, null);
        return exportBiz.exportStatisticsExpenditureCSV(expenditureDtos);
    }

    @RequestMapping("/statistics/project")
    public JSONObject project(HttpServletRequest request) {
        String dataSourceId = request.getParameter("dataSourceId");
        String keyWord = request.getParameter("keyword");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        String state = request.getParameter("state");
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit * (offset - 1);
        List<ProjectDto> projectDtos = projectBiz.statistics(dataSourceId, keyWord, startDate, endDate, state, limit, offset);
        return exportBiz.exportStatisticsProject(projectDtos);
    }

    @RequestMapping("/receivement/detail")
    public JSONObject receivementDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 年
        int year = Integer.valueOf(StringUtils.isEmpty(request.getParameter("year")) ? "0" : request.getParameter("year"));
        // 季度
        int quarter = Integer.valueOf(StringUtils.isEmpty(request.getParameter("quarter")) ? "0" : request.getParameter("quarter"));
        // 月
        int month = Integer.valueOf(StringUtils.isEmpty(request.getParameter("month")) ? "0" : request.getParameter("month"));
        if (year < 1 || year/1000 < 1 || year/1000 > 10) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = DateUtil.getStartDateByQuarter(year, quarter, month);
            endDate  = DateUtil.getEndDateByQuarter(year, quarter, month);
        } catch (Exception e) {
            result.put("code", 2001);
            result.put("msg", "时间解析有误");
            return result;
        }

        List<ReceivementEntity> receivementEntities = receivementBiz.statisticsDetail(startDate, endDate);

        return exportBiz.exportStatisticsReceivement(receivementEntities);
    }
}
