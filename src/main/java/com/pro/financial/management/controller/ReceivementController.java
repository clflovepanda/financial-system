package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.controller.view.ReceivementView;
import com.pro.financial.management.dao.entity.*;
import com.pro.financial.management.dto.AccountingLogDto;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.user.dao.entity.CompanyEntity;
import com.pro.financial.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receivement")
public class ReceivementController {

    @Autowired
    private ReceivementBiz receivementBiz;
    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;
    @Autowired
    private ReceivementTypeBiz receivementTypeBiz;
    @Autowired
    private RemitterMethodBiz remitterMethodBiz;
    @Autowired
    private AccountingLogBiz accountingLogBiz;
    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private DepositLogBiz depositLogBiz;
    @Autowired
    private ExpenditureBiz expenditureBiz;

    /**
     * @param jsonInfo
     * @param userId
     * @param flag     1 add 2 modify
     * @return
     */
    @RequestMapping("/add")
    public JSONObject addReceivement(
            @RequestBody JSONObject jsonInfo,
            @CookieValue("user_id") Integer userId,
            @RequestParam("flag") Integer flag) {
        JSONObject result = new JSONObject();
        if (userId < 1) {
            result.put("code", 1001);
            result.put("msg", "未获取到用户");
            return result;
        }
        ReceivementDto receivementDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivementDto.class);
        receivementDto.setUpdateUser(userId);
        receivementDto.setUtime(new Date());
        if (flag == 1) {
            receivementDto.setCreateUser(userId);
            receivementDto.setCtime(new Date());
            receivementDto.setState(0);
            int count = receivementBiz.addReceivement(receivementDto);
        } else {
            //获取已经认款金额
            BigDecimal updateMonye = subscriptionLogBiz.gethadSubscriptionTotalMoneyByRId(receivementDto.getId());
            if (updateMonye != null && receivementDto.getReceivementMoney().compareTo(updateMonye) == -1) {
                result.put("code", 8001);
                result.put("msg", "到款金额不能小于认款金额");
                return result;
            }
            if (updateMonye != null && receivementDto.getReceivementMoney().compareTo(updateMonye) == 1) {
                receivementDto.setState(2);
            }
            if (updateMonye != null && receivementDto.getReceivementMoney().compareTo(updateMonye) == 0) {
                receivementDto.setState(3);
            }
            int count = receivementBiz.updateReceivement(receivementDto);
            if (receivementDto.getState() != null) {
                receivementBiz.updateReceivementState(receivementDto.getId(), receivementDto.getState());
            }

        }
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //根据用户权限获取到能看到的到款id列表
        List<ReceivementView> receivementViewList = new ArrayList<>();
        String companyId = request.getParameter("companyId");
        String receivementTypeId = request.getParameter("receivementTypeId");
        String remitterMethodId = request.getParameter("remitterMethodId");
        String remitter = request.getParameter("remitter");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit * (offset - 1);
        List<ReceivementEntity> receivementEntities = receivementBiz.getList(null, companyId, receivementTypeId, remitterMethodId, remitter,
                startDate, endDate, limit, offset);
        int count = receivementBiz.getCount(companyId, receivementTypeId, remitterMethodId, remitter,
                startDate, endDate);
        if (CollectionUtils.isEmpty(receivementEntities)) {
            result.put("code", 0);
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            result.put("data", receivementEntities);
            return result;
        }
        List<Integer> receivementIds = receivementEntities.stream().map(ReceivementEntity::getId).collect(Collectors.toList());
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(receivementIds);
        //添加认款记录
        Map<Integer, List<SubscriptionLogEntity>> idAndSubscriptionLogEntitysMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(subscriptionLogEntities)) {
            for (SubscriptionLogEntity entity : subscriptionLogEntities) {
                int seid = entity.getReceivementId();
                if (idAndSubscriptionLogEntitysMap.containsKey(seid)) {
                    List<SubscriptionLogEntity> entities = idAndSubscriptionLogEntitysMap.get(seid);
                    entities.add(entity);
                } else {
                    List<SubscriptionLogEntity> entities = new ArrayList<>();
                    entities.add(entity);
                    idAndSubscriptionLogEntitysMap.put(seid, entities);
                }
            }
        }
        for (ReceivementEntity entity : receivementEntities) {
            ReceivementView view = new ReceivementView();
            view.setId(entity.getId());
            view.setCompanyId(entity.getCompanyId());
            view.setCompanyName(entity.getCoName());
            view.setReceivementTypeId(entity.getReceivementTypeId());
            view.setReceivementTypeName(entity.getReceivementTypeName());
            view.setReceivementMoney(entity.getReceivementMoney());
            view.setRemitterMethodId(entity.getRemitterMethodId());
            view.setRemitterMethodName(entity.getRemitterMethodName());
            view.setRemitter(entity.getRemitter());
            view.setReceiveDate(entity.getReceiveDate());
            if (!CollectionUtils.isEmpty(idAndSubscriptionLogEntitysMap) && !CollectionUtils.isEmpty(idAndSubscriptionLogEntitysMap.get(entity.getId()))) {
                BigDecimal hadSubscriptionTotalMoney = BigDecimal.ZERO;
                Date latestSubscriptionTime = new Date("1970/01/01 00:00:00");
                List<SubscriptionLogEntity> sentities = idAndSubscriptionLogEntitysMap.get(entity.getId());
                for (SubscriptionLogEntity sentity : sentities) {
                    hadSubscriptionTotalMoney = hadSubscriptionTotalMoney.add(sentity.getReceivementMoney());
                    if (latestSubscriptionTime.compareTo(sentity.getCtime()) < 0) {
                        latestSubscriptionTime = sentity.getCtime();
                    }
                }
                view.setHadSubscriptionTotalMoney(hadSubscriptionTotalMoney);
                view.setLatestSubscriptionTime(latestSubscriptionTime);
            } else {
                view.setHadSubscriptionTotalMoney(BigDecimal.ZERO);
                view.setLatestSubscriptionTime(new Date());
            }
            view.setRemaindSubscriptionTotalMoney(view.getReceivementMoney().subtract(view.getHadSubscriptionTotalMoney()));
            view.setState(entity.getState());
            view.setRemark(entity.getRemark());
            view.setCtime(entity.getCtime());
            view.setCreateUser(entity.getCreateUser());
            view.setUpdateUser(entity.getUpdateUser());
            view.setUtime(entity.getUtime());
            receivementViewList.add(view);
        }

        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", receivementViewList);
        result.put("count", count);
        return result;
    }

    @RequestMapping("/detail")
    public JSONObject getDetail(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        ReceivementEntity receivementEntity = receivementBiz.getById(id);
        // TODO 公司列表查询
        List<CompanyEntity> companyEntities = new ArrayList<>();
        List<ReceivementTypeEntity> receivementTypeEntities = receivementTypeBiz.getList();
        List<RemitterMethodEntity> remitterMethodEntities = remitterMethodBiz.getList();
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(new ArrayList<>(id));
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("receivementEntity", receivementEntity);
        result.put("companyEntities", companyEntities);
        result.put("receivementTypeEntities", receivementTypeEntities);
        result.put("remitterMethodEntities", remitterMethodEntities);
        result.put("subscriptionLogEntities", subscriptionLogEntities);
        return result;
    }

    /**
     * 删除  表state=5
     *
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    public JSONObject delete(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        List<RevenueEntity> revenueEntities = revenueBiz.getByReceivementId(id);
        List<RevenueEntity> deposit = new ArrayList<>();
        //判断是否有预收押金 并且退押金已经支付
        if (!CollectionUtils.isEmpty(revenueEntities)) {
            for (RevenueEntity revenueEntity : revenueEntities) {
                //7是预收押金判断是否支付
                if (revenueEntity.getRevenueTypeId() == 7) {
                    deposit.add(revenueEntity);
                    List<DepositLogEntity> depositLogEntities = depositLogBiz.getListByRevenueId(revenueEntity.getId());
                    if (!CollectionUtils.isEmpty(depositLogEntities)) {
                        result.put("code", 8001);
                        result.put("msg", "含有已经退回的押金无法删除");
                        return result;
//                        for (DepositLogEntity depositLogEntity : depositLogEntities) {
//                            ExpenditureEntity expenditureEntity = expenditureBiz.getById(depositLogEntity.getExpenditureId());
//                            //已经支付了 返回错误信息
//                            if (expenditureEntity.getState() == 4) {
//                                result.put("code", 8001);
//                                result.put("msg", "含有已经退回的押金无法删除");
//                                return result;
//                            }
//                        }
                    }
                }
            }
        }
        int count = receivementBiz.updateReceivementState(id, 5);
        if (count == 1) {
            //删除押金以及相关记录
            if (!CollectionUtils.isEmpty(deposit)) {
                for (RevenueEntity revenueEntity : deposit) {
                    List<DepositLogEntity> depositLogEntities = depositLogBiz.getListByRevenueIdWithoutState(revenueEntity.getId());
                    if (!CollectionUtils.isEmpty(depositLogEntities)) {
                        for (DepositLogEntity depositLogEntity : depositLogEntities) {
                            depositLogEntity.setState(0);
                            //修改押金记录表状态
                            depositLogBiz.updateById(depositLogEntity);
                            //修改支出表状态
                            expenditureBiz.deleteExpenditureByid(depositLogEntity.getExpenditureId());
                        }
                    }
                }
            }
            //删除成功删除认款日志和收入
            revenueBiz.deleteByReceivementId(id);
            subscriptionLogBiz.deleteByReceivementId(id);
            result.put("code", 0);
            result.put("msg", "");
            return result;
        }
        result.put("code", 7009);
        result.put("msg", "删除失败,未知错误!");
        return result;
    }

    /**
     * 做账
     *
     * @param request
     * @return
     */
    @RequestMapping("/accounting")
    public JSONObject accounting(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        Integer receivementId = Integer.valueOf(request.getParameter("receivementId"));
        Integer state = Integer.valueOf(request.getParameter("state"));
        String voucherNo = request.getParameter("voucherNo");
        // TODO 校验是否可以做账
        ReceivementEntity receivementEntity = receivementBiz.getById(receivementId);
        BigDecimal diff = receivementEntity.getReceivementMoney();
        List<Integer> receivementIds = new ArrayList<>();
        List<SubscriptionLogDto> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementId(receivementId);
        for (SubscriptionLogDto entity : subscriptionLogEntities) {
            diff = diff.subtract(entity.getReceivementMoney());
        }
        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("msg", "账目不平，无法做账");
            return result;
        }
        AccountingLogDto accountingLogDto = new AccountingLogDto();
        accountingLogDto.setReceivementId(receivementId);
        accountingLogDto.setVoucherNo(voucherNo);
        accountingLogDto.setState(state);
        accountingLogDto.setRemark("");
        accountingLogDto.setCreateUser(userId);
        accountingLogDto.setCtime(new Date());
        int count = accountingLogBiz.addAccountingLog(accountingLogDto);
        if (count == 1) {
            receivementBiz.updateReceivementState(receivementId, 4);
            result.put("code", 0);
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    @RequestMapping("/getsublog")
    public JSONObject getSubLog(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer receivementId = Integer.valueOf(request.getParameter("receivementId"));
        if (receivementId == null || receivementId < 1) {
            result.put("code", 1001);
            result.put("msg", "参数传入有误");
            return result;
        }
        List<SubscriptionLogDto> subscriptionLogDtos = subscriptionLogBiz.getListByReceivementId(receivementId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", subscriptionLogDtos);
        return result;
    }

    @RequestMapping("/addsublog")
    public JSONObject addSubscriptionLog(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        SubscriptionLogDto subscriptionLogDto = JSONObject.parseObject(jsonInfo.toJSONString(), SubscriptionLogDto.class);
        if (subscriptionLogDto == null || subscriptionLogDto.getReceivementId() == null) {
            result.put("code", 1001);
            result.put("msg", "未传入认款类型");
            return result;
        }
        //添加到认款记录的金额
        BigDecimal logMoney = BigDecimal.ZERO;
        List<SubscriptionLogDto> subscriptionLogDtos = subscriptionLogBiz.getListByReceivementId(subscriptionLogDto.getReceivementId());
        if (!CollectionUtils.isEmpty(subscriptionLogDtos)) {
            for (SubscriptionLogDto dto : subscriptionLogDtos) {
                logMoney = logMoney.add(dto.getReceivementMoney());
            }
        }
        ReceivementEntity entity = receivementBiz.getById(subscriptionLogDto.getReceivementId());
        if (entity.getReceivementMoney().compareTo(logMoney) == -1) {
            result.put("code", 1001);
            result.put("msg", "认款金额不足");
            return result;
        }
        Integer state = jsonInfo.getInteger("state");
        if (state == null || state != 3) {
            state = 2;
        }
        subscriptionLogDto.setState(1);
        subscriptionLogDto.setCreateUser(userId);
        subscriptionLogDto.setCtime(new Date());
        int count = subscriptionLogBiz.addSubscriptionLog(subscriptionLogDto);
        ReceivementEntity receivementEntity = receivementBiz.getById(subscriptionLogDto.getReceivementId());
        //全进入收入表 区分类型在不同场景 不在此处
        int countRevenue = revenueBiz.addRevenueBySubLog(subscriptionLogDto, userId);
        receivementBiz.updateReceivementState(subscriptionLogDto.getReceivementId(), state);

        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 删除认款纪录
     *
     * @return
     */
    @RequestMapping("/delsublog")
    public JSONObject delSublog(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.parseInt(StringUtils.isEmpty(request.getParameter("id")) ? "0" : request.getParameter("id"));
        if (id < 1) {
            result.put("code", 1001);
            result.put("msg", "传入参数有误");
            return result;
        }
        List<RevenueEntity> revenueEntities = revenueBiz.getBySubId(id);
        List<RevenueEntity> deposit = new ArrayList<>();
        //判断是否有预收押金 并且退押金已经支付
        if (!CollectionUtils.isEmpty(revenueEntities)) {
            for (RevenueEntity revenueEntity : revenueEntities) {
                //7是预收押金判断是否支付
                if (revenueEntity.getRevenueTypeId() == 7) {
                    deposit.add(revenueEntity);
                    List<DepositLogEntity> depositLogEntities = depositLogBiz.getListByRevenueId(revenueEntity.getId());
                    if (!CollectionUtils.isEmpty(depositLogEntities)) {
                        result.put("code", 8001);
                        result.put("msg", "含有已经退回的押金无法删除");
                        return result;
//                        for (DepositLogEntity depositLogEntity : depositLogEntities) {
//                            ExpenditureEntity expenditureEntity = expenditureBiz.getById(depositLogEntity.getExpenditureId());
//                            //已经支付了 返回错误信息
//                            if (expenditureEntity.getState() == 4) {
//                                result.put("code", 8001);
//                                result.put("msg", "含有已经退回的押金无法删除");
//                                return result;
//                            }
                    }
                }
            }
        }


        return subscriptionLogBiz.delSublog(id, deposit);
    }

}
