package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.dao.ProjectCompanyDao;
import com.pro.financial.management.dao.ProjectUserDao;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/project")
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

    @RequestMapping("/add")
    public JSONObject addProject(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        // 解析项目关联类目
        ProjectDataSourceDto projectDataSourceDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("projectDataSourceDto")).toString(), ProjectDataSourceDto.class);
        // 解析项目关联公司
        ProjectCompanyDto projectCompanyDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("projectCompanyDto")).toString(), ProjectCompanyDto.class);
        // 解析项目关联人员
        List<ProjectUserDto> projectUserDtos = JSON.parseArray(JSONObject.toJSON(jsonInfo.get("projectUserDto")).toString(), ProjectUserDto.class);
        // 解析关联工时
        ProjectDto projectDto = JSONObject.parseObject(jsonInfo.toJSONString(), ProjectDto.class);
        int count = projectBiz.addProject(projectDto);
        int projectId = projectDto.getId();
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
    public JSONObject getProjectList(HttpServletRequest request, @RequestBody ProjectDto projectDto) {
        JSONObject result = new JSONObject();
        // 权限过滤，过滤出所有可见项目ID TODO
        List<Integer> projectIds = new ArrayList<>();
        // 项目表
        List<ProjectEntity> projectEntities = projectBiz.getProjectList(projectIds);
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

        // 项目工时表 TODO
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("projectEntities", projectEntities);
        result.put("projectUserEntities", projectUserEntities);
        result.put("revenueEntities", revenueEntities);
        result.put("expenditureEntities", expenditureEntities);
        result.put("subscriptionLogEntities", subscriptionLogEntities);
        result.put("settlementEntities", settlementEntities);
        result.put("contractEntities", contractEntities);
        result.put("quotationEntities", quotationEntities);
        result.put("receivableEntities", receivableEntities);
        return result;
    }

    /**
     * 查看项目详情/修改项目页
     */
    @RequestMapping("/project_detail")
    public JSONObject getProjectDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        List<Integer> projectIds = new ArrayList<>(id);
        // 项目表
        List<ProjectEntity> projectEntities = projectBiz.getProjectList(projectIds);
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
        // 项目工时表 TODO
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("projectEntities", projectEntities);
        result.put("projectUserEntities", projectUserEntities);
        result.put("revenueEntities", revenueEntities);
        result.put("expenditureEntities", expenditureEntities);
        result.put("subscriptionLogEntities", subscriptionLogEntities);
        result.put("settlementEntities", settlementEntities);
        result.put("contractEntities", contractEntities);
        result.put("quotationEntities", quotationEntities);
        result.put("receivableEntities", receivableEntities);
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

}
