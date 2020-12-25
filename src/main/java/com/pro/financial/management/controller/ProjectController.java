package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.*;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.utils.ConvertUtil;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectBiz projectBiz;
    @Autowired
    private ProjectAuditLogBiz projectAuditLogBiz;
    @Autowired
    private ProjectDataSourceBiz projectDataSourceBiz;
    @Autowired
    private ProjectCompanyBiz projectCompanyBiz;
    @Autowired
    private ProjectUserBiz projectUserBiz;
    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;
    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;
    @Autowired
    private ReceivableBiz receivableBiz;
    @Autowired
    private SettlementBiz settlementBiz;
    @Autowired
    private ContractBiz contractBiz;
    @Autowired
    private QuotationBiz quotationBiz;
    @Autowired
    private ProjectTaskBiz projectTaskBiz;
    @Autowired
    private UserDao userDao;


    @RequestMapping("/add")
    public JSONObject addProject(@RequestBody JSONObject jsonInfo, @CookieValue("userId") Integer userId) {
        JSONObject result = new JSONObject();
        // 解析项目关联类目
        ProjectDataSourceDto projectDataSourceDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("projectDataSourceDto")).toString(), ProjectDataSourceDto.class);
        // 解析项目关联公司
        ProjectCompanyDto projectCompanyDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("projectCompanyDto")).toString(), ProjectCompanyDto.class);
        // 解析项目关联人员
        List<ProjectUserDto> projectUserDtos = JSON.parseArray(JSONObject.toJSON(jsonInfo.get("projectUserDto")).toString(), ProjectUserDto.class);
        // 解析关联工时
        ProjectDto projectDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("projectDto")).toString(), ProjectDto.class);
        int count = projectBiz.addProject(projectDto);
        int projectId = projectDto.getProjectId();
        // 处理项目关联类目表
        projectDataSourceDto.setProjectId(projectId + "");
        projectDataSourceDto.setCtime(new Date());
        projectDataSourceBiz.addProjectDataSource(projectDataSourceDto);
        // 处理项目关联公司表
        projectCompanyDto.setProjectId(projectId);
        projectCompanyDto.setCtime(new Date());
        projectCompanyBiz.addProjectCompany(projectCompanyDto);
        // 处理项目关联人员
        for (ProjectUserDto projectUserDto : projectUserDtos) {
            projectUserDto.setProjectId(projectId);
            projectUserDto.setCtime(new Date());
        }
        projectUserBiz.batchAddProjectUser(projectUserDtos);
        // 处理项目关联工时 TODO

        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看项目列表页
     */
    @RequestMapping("/project_list")
    public JSONObject getProjectList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 权限过滤，过滤出所有可见项目ID
        List<Integer> projectIds = projectDataSourceBiz.getProjectIdsByCookie(request);
        if (CollectionUtils.isEmpty(projectIds)) {
            result.put("code", 1001);
            result.put("msg", "无项目权限");
        }
        // 项目表
        List<ProjectEntity> projectEntities = projectBiz.getProjectList(projectIds);
        // 项目人员表
        List<ProjectUserEntity> projectUserEntities = projectUserBiz.getProjectUserList(projectIds);
        // 项目收入表
        List<RevenueEntity> revenueEntities = revenueBiz.getRevenueList(projectIds);
        // 项目支出表
//        List<ExpenditureEntity> expenditureEntities = expenditureBiz.getExpenditureList(projectIds);
        // 认款记录表
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByProjectIds(projectIds);
        // 结算单
        List<SettlementEntity> settlementEntities = settlementBiz.getListByProjectIds(projectIds);
        // 项目合同
        List<ContractEntity> contractEntities = contractBiz.getListByProjectIds(projectIds);
        // 报价单
        List<QuotationEntity> quotationEntities = quotationBiz.getListByProjectIds(projectIds);
        // 应收单
        List<ReceivableEntity> receivableEntities = receivableBiz.getListByProjectIds(projectIds);

        // 项目工时表 TODO
        List<ProjectTaskEntity> projectTaskEntities = projectTaskBiz.getListByProjectIds(projectIds);


        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        //封装参数到data
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("projectEntities", projectEntities);
        resultMap.put("projectUserEntities", projectUserEntities);
        resultMap.put("revenueEntities", revenueEntities);
//        resultMap.put("expenditureEntities", expenditureEntities);
        resultMap.put("subscriptionLogEntities", subscriptionLogEntities);
        resultMap.put("settlementEntities", settlementEntities);
        resultMap.put("contractEntities", contractEntities);
        resultMap.put("quotationEntities", quotationEntities);
        resultMap.put("receivableEntities", receivableEntities);
        resultMap.put("projectTaskEntities", projectTaskEntities);
        result.put("data", resultMap);
        return result;
    }

    /**
     * 查看项目详情/修改项目页
     */
    @RequestMapping("/project_detail")
    public JSONObject getProjectDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        List<Integer> projectIds = new ArrayList<>();
        projectIds.add(id);
        // 项目表
        List<ProjectEntity> projectEntities = projectBiz.getProjectList(projectIds);
        if (CollectionUtils.isEmpty(projectEntities)) {
            result.put("code", 10001);
            result.put("msg", "未查询到项目");
            return result;
        }
        // 项目人员表
        List<ProjectUserEntity> projectUserEntities = projectUserBiz.getProjectUserList(projectIds);
        // 项目收入表
        List<RevenueEntity> revenueEntities = revenueBiz.getRevenueList(projectIds);
        // 项目支出表
        List<ExpenditureEntity> expenditureEntities = expenditureBiz.getExpenditureList(projectIds);
        // 认款记录表
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByProjectIds(projectIds);
        // 结算单
        List<SettlementEntity> settlementEntities = settlementBiz.getListByProjectIds(projectIds);
        // 项目合同
        List<ContractEntity> contractEntities = contractBiz.getListByProjectIds(projectIds);
        // 报价单
        List<QuotationEntity> quotationEntities = quotationBiz.getListByProjectIds(projectIds);
        // 应收单
        List<ReceivableEntity> receivableEntities = receivableBiz.getListByProjectIds(projectIds);
        //审核人
        ProjectAuditLogDto projectAuditLogDto = projectAuditLogBiz.getProjectAuditByProjectId(id);
        ProjectEntity projectEntity = projectEntities.get(0);
        String company = projectCompanyBiz.getCompanyByProjectId(id);
        projectEntity.setCompany(company);
        int createUser = projectEntity.getCreateUser();
        projectEntity.setCreateUserName(userDao.getUserById(createUser).getUsername());
        // 项目工时表 TODO
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        //封装参数到data
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("projectEntities", projectEntities);
        resultMap.put("projectUserEntities", projectUserEntities);
        resultMap.put("revenueEntities", revenueEntities);
        resultMap.put("expenditureEntities", expenditureEntities);
        resultMap.put("subscriptionLogEntities", subscriptionLogEntities);
        resultMap.put("settlementEntities", settlementEntities);
        resultMap.put("contractEntities", contractEntities);
        resultMap.put("quotationEntities", quotationEntities);
        resultMap.put("receivableEntities", receivableEntities);
        resultMap.put("projectAuditLog", projectAuditLogDto);
        result.put("data", resultMap);
        return result;
    }

    /**
     * 开启/关闭/暂停项目
     */
    @RequestMapping("/project_state")
    public JSONObject getProjectClose(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer state = Integer.valueOf(request.getParameter("project_state"));
        int updateResult = projectBiz.updateState(id, state);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 销售提成已发放   1  部分发放  2 全部发放
     */
    @RequestMapping("/distribute_sales_commission")
    public JSONObject distributeSalesCommission1(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer saleCommisState = Integer.valueOf(request.getParameter("sale_commis_state"));
        int updateResult = projectBiz.updateSaleCommisState(id, saleCommisState);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 项目审核通过/驳回 audit_state = 1 通过   2 驳回
     */
    @RequestMapping("/project_audit")
    public JSONObject approveProjectAudit(HttpServletRequest request, @CookieValue("userId") Integer userId) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer auditState = Integer.valueOf(request.getParameter("audit_state"));
        int updateResult = projectBiz.updateAuditState(id, auditState);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            ProjectAuditLogDto projectAuditLogDto = new ProjectAuditLogDto();
            projectAuditLogDto.setProjectId(id);
            projectAuditLogDto.setAuditType(auditState);
            projectAuditLogDto.setCreateUser(userId);
            projectAuditLogDto.setCtime(new Date());
            projectAuditLogDto.setState(0);
            projectAuditLogBiz.addProjectAuditLog(projectAuditLogDto);
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;

    }

    /**
     * 查看项目列表页
     */
    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 权限过滤，过滤出所有可见项目ID
        List<Integer> projectIds = projectDataSourceBiz.getProjectIdsByCookie(request);
        if (CollectionUtils.isEmpty(projectIds)) {
            result.put("code", 1001);
            result.put("msg", "无项目权限");
        }
        String projectNo = request.getParameter("projectNo");
        String projectName = request.getParameter("projectName");
        //项目经理
        String managerName = request.getParameter("managerName");
        //销售经理
        String salesName = request.getParameter("salesName");
        //结算单状态
        String settlementState = request.getParameter("settlementState");
        //项目状态
        String state = request.getParameter("state");
        //销售提成发放状态
        String saleCommisState = request.getParameter("saleCommisState");
        String userNames = request.getParameter("userNames");
        //项目时间
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        //审核状态
        String auditingState = request.getParameter("auditing_state");
        if (StringUtils.isEmpty(auditingState)) {
            auditingState = "1";
        }
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startDt) && StringUtils.isNotEmpty(endDt)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                startDate = simpleDateFormat.parse(startDt);
                endDate = simpleDateFormat.parse(endDt);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        List<ProjectEntity> projectEntities = projectBiz.getList(projectIds, projectNo, projectName, managerName, salesName, userNames, settlementState, state, saleCommisState, startDate, endDate, auditingState);
        // 项目人员表
        List<ProjectUserEntity> projectUserEntities = projectUserBiz.getProjectUserList(projectIds);
        // 项目收入表
        List<RevenueEntity> revenueEntities = revenueBiz.getRevenueList(projectIds);
        // 项目支出表
        List<ExpenditureEntity> expenditureEntities = expenditureBiz.getExpenditureList(projectIds);
        //结算单
        List<SettlementEntity> settlementEntities = settlementBiz.getListByProjectIds(projectIds);
        // 认款记录表
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByProjectIds(projectIds);
        List<ProjectDto> projectDtos = ConvertUtil.convert(ProjectEntity2Dto.instance, projectEntities);
        if (!CollectionUtils.isEmpty(projectDtos)) {
            for (ProjectDto projectDto : projectDtos) {
                //设置总支出和收入
                Integer projectId = projectDto.getProjectId();
                BigDecimal paymentIncome = new BigDecimal(0);
                BigDecimal paymentExpenses = new BigDecimal(0);
                BigDecimal settlementIncome = new BigDecimal(0);
                BigDecimal settlementExpenses = new BigDecimal(0);
                for (RevenueEntity revenueEntity : revenueEntities) {
                    if (revenueEntity.getProjectId() - projectId == 0) {
                        paymentIncome = paymentIncome.add(revenueEntity.getCnyMoney() == null ? new BigDecimal(0) : revenueEntity.getCnyMoney());
                    }
                }
                for (ExpenditureEntity expenditureEntity : expenditureEntities) {
                    if (expenditureEntity.getProjectId() - projectId == 0) {
                        paymentExpenses = paymentExpenses.add(expenditureEntity.getExpenditureMoney() == null ? new BigDecimal(0) : expenditureEntity.getExpenditureMoney());
                    }
                }
                for (SettlementEntity settlementEntity : settlementEntities) {
                    if (settlementEntity.getProjectId() - projectId == 0) {
                        settlementIncome = settlementIncome.add(settlementEntity.getSettlementIncome() == null ? new BigDecimal(0) : settlementEntity.getSettlementIncome());
                        settlementExpenses = settlementExpenses.add(settlementEntity.getSettlementExpenses() == null ? new BigDecimal(0) : settlementEntity.getSettlementExpenses());
                    }
                }
                projectDto.setPaymentIncome(paymentIncome);
                projectDto.setPaymentExpenses(paymentExpenses);
                projectDto.setSettlementIncome(settlementIncome);
                projectDto.setSettlementExpenses(settlementExpenses);
            }
        }


        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", projectDtos);
        return result;
    }

}
