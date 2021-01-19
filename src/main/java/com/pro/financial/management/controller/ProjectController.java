package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.converter.ProjectCompanyDto2Entity;
import com.pro.financial.management.converter.ProjectDataSourceDto2Entity;
import com.pro.financial.management.converter.ProjectDto2Entity;
import com.pro.financial.management.converter.ProjectEntity2Dto;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.*;
import com.pro.financial.user.dao.UserDao;
import com.pro.financial.user.dao.entity.DataSourceEntity;
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
    @Autowired
    private InvoiceBiz invoiceBiz;


    @RequestMapping("/add")
    public JSONObject addProject(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ProjectDto projectDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("project")).toString(), ProjectDto.class);
        if (projectDto.getDataSourceId() == null) {
            result.put("code", 1001);
            result.put("msg", "参数有误");
            return result;
        }
        List<DataSourceEntity> dataSourceEntities = userDao.getDataSource(userId);
        boolean flag = false;
        if (CollectionUtils.isEmpty(dataSourceEntities)) {
            result.put("code", 7001);
            result.put("msg", "无立项权限");
            return result;
        } else {
            for (DataSourceEntity dataSourceEntity : dataSourceEntities) {
                if (dataSourceEntity.getDataSourceId() - Integer.parseInt(projectDto.getDataSourceId()) == 0) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            result.put("code", 7001);
            result.put("msg", "无立项权限");
            return result;
        }
        // 解析项目关联类目
        ProjectDataSourceDto projectDataSourceDto = new ProjectDataSourceDto();
        projectDataSourceDto.setDataSourceId(projectDto.getDataSourceId());
        // 解析项目关联公司
        ProjectCompanyDto projectCompanyDto = new ProjectCompanyDto();
        projectCompanyDto.setCompanyId(projectDto.getCompanyId());
        // 解析项目关联人员
        List<ProjectUserDto> projectUserDtos = new ArrayList<>();
        // 解析关联工时

        //生成编号
        String projectNo;
        //获取最后一条数据的编号
        String lastNo = projectBiz.selectLastNo();
        if (StringUtils.isEmpty(lastNo)) {
            lastNo = "001";
        }
//        projectNo = CommonUtil.generatorNO(CommonConst.initials_project, projectDto.getDataSourceName(), lastNo);
        projectNo = System.currentTimeMillis()/1000 + "";
        projectDto.setCode(projectNo);
        projectDto.setCreateUser(userId);
        projectDto.setCtime(new Date());
        projectDto.setUpdateUser(userId);
        projectDto.setUtime(new Date());
        projectDto.setFullname(projectDto.getName());
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

        //项目经理
        ProjectUserDto manage = new ProjectUserDto();
        manage.setProjectId(projectId);
        manage.setUserId(projectDto.getManagerId());
        manage.setType(2);
        manage.setCtime(new Date());
        projectUserDtos.add(manage);
        //销售经理
        ProjectUserDto sales = new ProjectUserDto();
        sales.setProjectId(projectId);
        sales.setUserId(projectDto.getSalesId());
        sales.setType(1);
        sales.setCtime(new Date());
        projectUserDtos.add(sales);
        // 处理项目关联人员
        for (Integer otherUserId : projectDto.getUserIds()) {
            ProjectUserDto projectUserDto = new ProjectUserDto();
            projectUserDto.setProjectId(projectId);
            projectUserDto.setUserId(otherUserId);
            projectUserDto.setType(3);
            projectUserDto.setCtime(new Date());
            projectUserDtos.add(projectUserDto);
        }
        projectUserBiz.batchAddProjectUser(projectUserDtos);
        // 处理项目关联工时 TODO

        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看项目列表页
     */
    @RequestMapping("/project_list")
    public JSONObject getProjectList(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        // 权限过滤，过滤出所有可见项目ID
        List<Integer> projectIds = projectDataSourceBiz.getProjectIdsByCookie(request, userId);
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


        result.put("code", 0);
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
        Integer projectId = Integer.valueOf(request.getParameter("id"));
        List<Integer> projectIds = new ArrayList<>();
        projectIds.add(projectId);
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
        ProjectAuditLogDto projectAuditLogDto = projectAuditLogBiz.getProjectAuditByProjectId(projectId);
        ProjectEntity projectEntity = projectEntities.get(0);
        String company = projectCompanyBiz.getCompanyByProjectId(projectId);
        projectEntity.setCompany(company);
        int createUser = projectEntity.getCreateUser();
        projectEntity.setCreateUserName(userDao.getUserById(createUser).getUsername());
        // 项目工时表 TODO
        //财务统计 TODO
        ProjectFinancialStatisticsDto projectFinancialStatisticsDto = new ProjectFinancialStatisticsDto();
        projectFinancialStatisticsDto.setEstincome(projectEntity.getEstincome());
        projectFinancialStatisticsDto.setBudget(projectEntity.getBudget());
        //实际收入 (无押金和预收押金)
        BigDecimal realRevenue = revenueBiz.getreByProjectId(projectId, "") == null ? new BigDecimal(0) : revenueBiz.getreByProjectId(projectId, "");
        projectFinancialStatisticsDto.setActualIncome(realRevenue);
        //实际支出(已经提交, 已经支付, 平借款)
        BigDecimal realExpenditure = expenditureBiz.getexByProjectId(projectId) == null ? new BigDecimal(0) : expenditureBiz.getexByProjectId(projectId);
        projectFinancialStatisticsDto.setActualExpenditure(realExpenditure);
        //预收押金
        BigDecimal deposit = revenueBiz.getreByProjectId(projectId, "Y") == null ? new BigDecimal(0) : revenueBiz.getreByProjectId(projectId, "Y");
        projectFinancialStatisticsDto.setDeposit(deposit);
        //押金转收入
        BigDecimal deposit2Re = revenueBiz.getreByProjectId(projectId, "S") == null ? new BigDecimal(0) : revenueBiz.getreByProjectId(projectId, "S");
        projectFinancialStatisticsDto.setDepositIncome(deposit2Re);
        //项目利润
        BigDecimal profit = BigDecimal.ZERO;
        //实际收入（收回押金+预收押金+认款 ）
        BigDecimal revenue = profit.add(deposit).add(realRevenue);
        profit = revenue.subtract(realExpenditure);
        projectFinancialStatisticsDto.setProfit(profit);
        //毛利率
        BigDecimal rate = BigDecimal.ZERO;
        if (!(revenue.compareTo(new BigDecimal(0)) == 0)) {
            rate = profit.divide(revenue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        projectFinancialStatisticsDto.setRate(rate.doubleValue());

        //支出比 = 实际支出 （提交的+ 已支付+平借款的）/  实际收入（收回押金+预收押金+认款 ）
        BigDecimal ratio = BigDecimal.ZERO;
        if (!(revenue.compareTo(new BigDecimal(0)) == 0)) {
            ratio = realExpenditure.divide(revenue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        projectFinancialStatisticsDto.setExpenditureRatio(ratio.doubleValue());
        //结算收入
        BigDecimal settlementIncome = settlementBiz.getreByProjectId(projectId) == null ? BigDecimal.ZERO : settlementBiz.getreByProjectId(projectId);
        projectFinancialStatisticsDto.setSettlement(settlementIncome);
        //应收收入
        projectFinancialStatisticsDto.setReceivable(settlementIncome.subtract(realExpenditure));

        //人工成本
        BigDecimal timeMoney = BigDecimal.ZERO;
        projectFinancialStatisticsDto.setTimeMoney(timeMoney);
        //项目纯利润
        BigDecimal relProfit = profit.subtract(timeMoney);
        projectFinancialStatisticsDto.setRelProfit(relProfit);
        //纯利率
        BigDecimal relRate = BigDecimal.ZERO;
        if (!(revenue.compareTo(new BigDecimal(0)) == 0)) {
            relRate = relProfit.divide(revenue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        projectFinancialStatisticsDto.setRelRate(relRate.doubleValue());



        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        Integer dataSourceId = 0;
        Integer companyId = 0;
        if (!CollectionUtils.isEmpty(projectEntities)) {
            int dsId = projectEntities.get(0).getDataSourceId();
            if (dsId > 0) {
                dataSourceId = projectBiz.getParentDSId(dsId);
            }
            companyId = projectCompanyBiz.getCompanyIdByProjectId(projectEntities.get(0).getProjectId());
        }
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
        resultMap.put("financial", projectFinancialStatisticsDto);
        resultMap.put("dataSourceId", dataSourceId);
        resultMap.put("companyId", companyId);
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
            result.put("code", 0);
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
            result.put("code", 0);
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
    public JSONObject approveProjectAudit(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        Integer projectId = Integer.valueOf(request.getParameter("id"));
        Integer auditState = Integer.valueOf(request.getParameter("auditing_state"));
        ProjectAuditLogDto projectAuditLog = projectAuditLogBiz.getProjectAuditByProjectId(projectId);
//        if (projectAuditLog != null) {
//            result.put("code", 1001);
//            result.put("msg", "项目已经审核");
//            return result;
//        }
        //修改项目状态
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setState(auditState + 1);
        projectEntity.setAuditingState(auditState);
        boolean updateResult = projectBiz.updateById(projectEntity);
        if (updateResult) {
            ProjectAuditLogDto projectAuditLogDto = new ProjectAuditLogDto();
            projectAuditLogDto.setProjectId(projectId);
            projectAuditLogDto.setAuditType(auditState);
            projectAuditLogDto.setCreateUser(userId);
            projectAuditLogDto.setCtime(new Date());
            projectAuditLogDto.setState(1);
            projectAuditLogBiz.addProjectAuditLog(projectAuditLogDto);

            result.put("code", 0);
            result.put("msg", HttpStatus.OK.getReasonPhrase());
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
    public JSONObject getList(HttpServletRequest request,@CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        // 权限过滤，过滤出所有可见项目ID
        List<Integer> projectIds = projectDataSourceBiz.getProjectIdsByCookie(request, userId);
        if (CollectionUtils.isEmpty(projectIds)) {
            result.put("code", 1001);
            result.put("msg", "无项目权限");
            return result;
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
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit*(offset - 1);
        //审核状态
        String auditingState = request.getParameter("auditing_state");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ProjectEntity> projectEntities = projectBiz.getList(projectIds, projectNo, projectName, managerName, salesName,
                userNames, settlementState, state, saleCommisState, startDate, endDate, auditingState, limit, offset);
        //数量
        int count = projectBiz.getCount(projectIds, projectNo, projectName, managerName, salesName,
                userNames, settlementState, state, saleCommisState, startDate, endDate, auditingState);
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
                BigDecimal paymentIncome = BigDecimal.ZERO;
                BigDecimal paymentExpenses = BigDecimal.ZERO;
                BigDecimal settlementIncome = BigDecimal.ZERO;
                BigDecimal settlementExpenses = BigDecimal.ZERO;
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

//                //实际收入
//                BigDecimal realRevenue = revenueBiz.getreByProjectId(projectId, "");
//                //预收押金
//                BigDecimal deposit = revenueBiz.getreByProjectId(projectId, "Y");
//                //押金转收入
//                BigDecimal deposit2Re = revenueBiz.getreByProjectId(projectId, "S");
//                //实际支出
//                BigDecimal realExpenditure = expenditureBiz.getexByProjectId(projectId);
                //利润率
                paymentIncome = paymentIncome == null ? new BigDecimal(0) : paymentIncome;
                paymentExpenses = paymentExpenses == null ? new BigDecimal(0) : paymentExpenses;
                projectDto.setPaymentProfit(paymentIncome.subtract(paymentExpenses).doubleValue());
                //毛利率


                BigDecimal rate = BigDecimal.ZERO;
                if (!(paymentIncome.compareTo(new BigDecimal(0)) == 0)) {
                    rate = paymentIncome.subtract(paymentExpenses);
                    rate = rate.divide(paymentIncome, 2, BigDecimal.ROUND_HALF_UP);
                }
                projectDto.setProjectRate(rate.doubleValue());
            }
        }


        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", projectDtos);
        result.put("count", count);
        return result;
    }

    @RequestMapping("/getbykey")
    public JSONObject getProjectByKeywords(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWords = request.getParameter("keyWords");
        List<ProjectDto> projectDtos = projectBiz.getProjectByKeywords(keyWords);
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", projectDtos);
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateProject(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ProjectDto projectDto = JSONObject.parseObject(JSONObject.toJSON(jsonInfo.get("project")).toString(), ProjectDto.class);
        if (projectDto.getProjectId() == null || projectDto.getProjectId() < 0) {
            result.put("code", 1001);
            result.put("msg", "参数有误");
            return result;
        }
        if (projectDto.getDataSourceId() == null) {
            result.put("code", 1001);
            result.put("msg", "参数有误");
            return result;
        }
        List<DataSourceEntity> dataSourceEntities = userDao.getDataSource(userId);
        if (CollectionUtils.isEmpty(dataSourceEntities)) {
            result.put("code", 7001);
            result.put("msg", "无立项权限");
            return result;
        } else {
            boolean dataFl = false;
            for (DataSourceEntity dataSourceEntity : dataSourceEntities) {
                if (dataSourceEntity.getDataSourceId() - Integer.parseInt(projectDto.getDataSourceId()) == 0) {
                    dataFl = true;
                    break;
                }
            }
            if (! dataFl) {
                result.put("code", 7001);
                result.put("msg", "无立项权限");
                return result;
            }
        }
        // 解析项目关联类目
        ProjectDataSourceDto projectDataSourceDto = new ProjectDataSourceDto();
        projectDataSourceDto.setDataSourceId(projectDto.getDataSourceId());
        // 解析项目关联公司
        ProjectCompanyDto projectCompanyDto = new ProjectCompanyDto();
        projectCompanyDto.setCompanyId(projectDto.getCompanyId());
        // 解析项目关联人员
        List<ProjectUserDto> projectUserDtos = new ArrayList<>();
        // 解析关联工时

        projectDto.setUpdateUser(userId);
        projectDto.setUtime(new Date());
        projectDto.setFullname(projectDto.getName());
        projectBiz.updateById(ProjectDto2Entity.instance.convert(projectDto));
        int projectId = projectDto.getProjectId();
        // 处理项目关联类目表
        projectDataSourceDto.setProjectId(projectId + "");
        projectDataSourceDto.setCtime(new Date());
        QueryWrapper<ProjectDataSourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", projectId);
        projectDataSourceBiz.update(ProjectDataSourceDto2Entity.instance.convert(projectDataSourceDto), queryWrapper);
        // 处理项目关联公司表
        projectCompanyDto.setProjectId(projectId);
        projectCompanyDto.setCtime(new Date());
        projectCompanyBiz.updateById(ProjectCompanyDto2Entity.instance.convert(projectCompanyDto));

        //项目经理
        ProjectUserDto manage = new ProjectUserDto();
        manage.setProjectId(projectId);
        manage.setUserId(projectDto.getManagerId());
        manage.setType(2);
        manage.setCtime(new Date());
        projectUserDtos.add(manage);
        //销售经理
        ProjectUserDto sales = new ProjectUserDto();
        sales.setProjectId(projectId);
        sales.setUserId(projectDto.getSalesId());
        sales.setType(1);
        sales.setCtime(new Date());
        projectUserDtos.add(sales);
        // 处理项目关联人员
        for (Integer otherUserId : projectDto.getUserIds()) {
            ProjectUserDto projectUserDto = new ProjectUserDto();
            projectUserDto.setProjectId(projectId);
            projectUserDto.setUserId(otherUserId);
            projectUserDto.setType(3);
            projectUserDto.setCtime(new Date());
            projectUserDtos.add(projectUserDto);
        }
        projectUserBiz.deleteByProjectId(projectId);
        projectUserBiz.batchUpdateProjectUser(projectUserDtos);
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

}
