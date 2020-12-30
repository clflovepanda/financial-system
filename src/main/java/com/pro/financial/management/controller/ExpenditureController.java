package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.converter.ExpenditurePurposeEntity2Dto;
import com.pro.financial.management.converter.ExpenditureTypeEntity2Dto;
import com.pro.financial.management.dao.entity.AccountingLogEntity;
import com.pro.financial.management.dao.entity.BeneficiaryUnitEntity;
import com.pro.financial.management.dao.entity.DepositLogEntity;
import com.pro.financial.management.dto.*;
import com.pro.financial.utils.CommonUtil;
import com.pro.financial.utils.ConvertUtil;
import com.pro.financial.utils.SimpleMoneyFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private RevenueTypeBiz revenueTypeBiz;
    @Autowired
    private BeneficiaryUnitBiz beneficiaryUnitBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditure(@RequestBody JSONObject jsonInfo, HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
//        Integer revenueId = jsonInfo.getInteger("revenueId");
//        RevenueDto revenueDto =  revenueBiz.getByRevenueId(revenueId);
        //生成编号
        String numbering;
        //获取最后一条数据的编号
        String lastNo = expenditureBiz.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            lastNo = "001";
        }
        numbering = CommonUtil.generatorNO(CommonConst.initials_expenditure, "", lastNo);
        expenditureDto.setExpenditureType(expenditureDto.getExpenditureTypeId());
        expenditureDto.setNumbering(numbering);
        expenditureDto.setMoneyCapital(SimpleMoneyFormat.getInstance().format(expenditureDto.getExpenditureMoney()));
        expenditureDto.setCreateUser(userId);
        expenditureDto.setCtime(new Date());
        expenditureDto.setUpdateUser(userId);
        expenditureDto.setUtime(new Date());
        int count = expenditureBiz.addExpenditure(expenditureDto);
        //添加银行信息
        try {
            BeneficiaryUnitEntity beneficiaryUnitEntity = JSONObject.parseObject(JSONObject.toJSONString(expenditureDto), BeneficiaryUnitEntity.class);
            beneficiaryUnitBiz.save(beneficiaryUnitEntity);
        } catch (Exception e) {

        }
        //退押金操作
        if (StringUtils.equals("deposit", request.getParameter("flag"))) {
            //depositId 为收入表revenueId
            Integer depositId = jsonInfo.getInteger("revenueId");
            RevenueDto revenueDto =  revenueBiz.getByRevenueId(depositId);
            //这笔押金总款
            BigDecimal revenueMoney =  revenueDto.getCnyMoney();
            //已经退回款项和退回中款项
            BigDecimal depositMoney = new BigDecimal(0);
            List<DepositLogEntity> depositLogEntities = depositLogBiz.getListByRevenueId(depositId);
            for (DepositLogEntity entity : depositLogEntities) {
                depositMoney = depositMoney.add(entity.getExpenditureMoney() == null ? new BigDecimal(0) : entity.getExpenditureMoney());
            }
            if (revenueMoney.compareTo(depositMoney) == -1) {
                result.put("code", 7001);
                result.put("msg", "剩余金额不足");
                return result;
            }
            DepositLogEntity depositLogEntity = new DepositLogEntity();
            depositLogEntity.setExpenditureId(expenditureDto.getExpenditureId());
            depositLogEntity.setRevenueId(depositId);
            depositLogBiz.save(depositLogEntity);
        }
        //添加申请工作流
        ExpenditureAuditLogDto expenditureAuditLogDto = new ExpenditureAuditLogDto();
        expenditureAuditLogDto.setExpenditureId(expenditureDto.getExpenditureId());
        //已经提交
        expenditureAuditLogDto.setAuditType(1);
        expenditureAuditLogDto.setCreateUser(userId);
        expenditureAuditLogBiz.addExpenditureAuditLog(expenditureAuditLogDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateExpenditure(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureDto expenditureDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureDto.class);
        int count = expenditureBiz.updateExpenditure(expenditureDto);
        result.put("code", 0);
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
        String projectName = request.getParameter("projectName");
        String projectNo = request.getParameter("projectNo");

        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ExpenditureDto> expenditureDtos = expenditureBiz.searchList(projectId, companyId, numbering, expenditureMethodId, expenditureTypeId,
                beneficiaryUnit, createUser, state, expenditureAuditLog, expenditurePurposeId, startDate, endDate, keyWord, projectName, projectNo);
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
        List<RevenueTypeDto> revenueTypeDtos = revenueTypeBiz.getType("");
        resultMap.put("type", expenditureTypeDtos);
        resultMap.put("purpose", expenditurePurposeDtos);
        resultMap.put("revenueType", revenueTypeDtos);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resultMap);
        return result;
    }

    @RequestMapping("/approval")
    public JSONObject approval(HttpServletRequest request, @RequestBody ExpenditureAuditLogDto expenditureAuditLogDto, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        if (expenditureAuditLogDto.getExpenditureId() == null || expenditureAuditLogDto.getExpenditureId() < 1) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        ExpenditureAuditLogDto lastLog = new ExpenditureAuditLogDto();
        List<ExpenditureAuditLogDto> expenditureAuditLogDtos = expenditureAuditLogBiz.getLogByEId(expenditureAuditLogDto.getExpenditureId());
        if (!CollectionUtils.isEmpty(expenditureAuditLogDtos)) {
            List<ExpenditureAuditLogDto> sortList = expenditureAuditLogDtos.stream().sorted(Comparator.comparing(ExpenditureAuditLogDto :: getCtime).reversed()).collect(Collectors.toList());
            lastLog = sortList.get(0);
            //状态为 3,4,5,6 不可继续操作
            if (lastLog.getAuditType() > 2) {
                result.put("code", 5001);
                result.put("msg", "已经是最终状态");
                return result;
            }
            if (expenditureAuditLogDto.getAuditType() > 2 && lastLog.getAuditType() < 3) {
                //添加审批记录
                expenditureAuditLogDto.setCreateUser(userId);
                expenditureAuditLogDto.setCtime(new Date());
                expenditureAuditLogBiz.addExpenditureAuditLog(expenditureAuditLogDto);
                //修改支出表状态
                ExpenditureDto expenditureDto = new ExpenditureDto();
                expenditureDto.setExpenditureId(expenditureAuditLogDto.getExpenditureId());
                expenditureDto.setState(expenditureAuditLogDto.getAuditType());
                expenditureBiz.updateExpenditure(expenditureDto);
                //已经支付 如果更改押金表记录
                if (expenditureAuditLogDto.getAuditType() - CommonConst.expenditure_audit_type_paid == 0) {
                    QueryWrapper<DepositLogEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("expenditure_id", expenditureAuditLogDto.getExpenditureId());
                    DepositLogEntity depositLogEntity = new DepositLogEntity();
                    depositLogEntity.setState(1);
                    depositLogBiz.update(depositLogEntity, queryWrapper);
                }
            }
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
    @RequestMapping("/approval/del")
    public JSONObject approvalDel(HttpServletRequest request, @RequestBody ExpenditureAuditLogDto expenditureAuditLogDto, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ExpenditureAuditLogDto lastLog = new ExpenditureAuditLogDto();
        List<ExpenditureAuditLogDto> expenditureAuditLogDtos = expenditureAuditLogBiz.getLogByEId(expenditureAuditLogDto.getExpenditureId());
        if (!CollectionUtils.isEmpty(expenditureAuditLogDtos)) {
            List<ExpenditureAuditLogDto> sortList = expenditureAuditLogDtos.stream().sorted(Comparator.comparing(ExpenditureAuditLogDto::getCtime).reversed()).collect(Collectors.toList());
            lastLog = sortList.get(0);
        }
        if (lastLog.getId() - expenditureAuditLogDto.getId() != 0) {
            result.put("code", 4001);
            result.put("msg", "不是最新记录无法删除");
            return result;
        }
        if (expenditureAuditLogDtos.size() == 1 || lastLog.getAuditType() - CommonConst.expenditure_audit_type_submit == 0) {
            result.put("code", 4001);
            result.put("msg", "无法删除提交记录");
            return result;
        }
        if (lastLog.getId() - expenditureAuditLogDto.getId() == 0 && expenditureAuditLogDto.getCreateUser() - userId == 0) {
            expenditureAuditLogBiz.remove(expenditureAuditLogDto);
            ExpenditureAuditLogDto nextLog = expenditureAuditLogDtos.get(1);
            ExpenditureDto expenditureDto = new ExpenditureDto();
            expenditureDto.setExpenditureId(expenditureAuditLogDto.getExpenditureId());
            expenditureDto.setState(nextLog.getAuditType());
            expenditureBiz.updateExpenditure(expenditureDto);

            //已经支付 如果更改押金表记录
            if (expenditureAuditLogDto.getAuditType() - CommonConst.expenditure_audit_type_paid == 0) {
                QueryWrapper<DepositLogEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("expenditure_id", expenditureAuditLogDto.getExpenditureId());
                DepositLogEntity depositLogEntity = new DepositLogEntity();
                depositLogEntity.setState(0);
                depositLogBiz.update(depositLogEntity, queryWrapper);
            }

        } else {
            result.put("code", 4001);
            result.put("msg", "非本人提交无法删除");
            return result;
        }


        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getbeneficiary")
    public JSONObject getBeneficiaryUnit(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWords = request.getParameter("keyWords");
        List<BeneficiaryUnitEntity> beneficiaryUnitEntities = beneficiaryUnitBiz.selectByKeyWords(keyWords);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", beneficiaryUnitEntities);
        return result;

    }
}
