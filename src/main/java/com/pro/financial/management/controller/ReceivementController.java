package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.*;
import com.pro.financial.management.controller.view.ReceivementView;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.AccountingLogDto;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.user.dao.entity.CompanyEntity;
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
    private ProjectDataSourceBiz projectDataSourceBiz;

    /**
     *
     * @param jsonInfo
     * @param userId
     * @param flag 1 add 2 modify
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
        receivementDto.setCreateUser(userId);
        receivementDto.setCtime(new Date());
        receivementDto.setUpdateUser(userId);
        receivementDto.setUtime(new Date());
        if (flag == 1) {
            receivementDto.setCreateUser(userId);
            receivementDto.setCtime(new Date());
            receivementDto.setState(0);
            int count = receivementBiz.addReceivement(receivementDto);
        } else {
            int count = receivementBiz.updateReceivement(receivementDto);
        }
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //根据用户权限获取到能看到的到款id列表
        List<ReceivementView> receivementViewList = new ArrayList<>();
        List<Integer> ids = projectDataSourceBiz.getProjectIdsByCookie(request);
        String companyId = request.getParameter("companyId");
        String receivementTypeId = request.getParameter("receivementTypeId");
        String remitterMethodId = request.getParameter("remitterMethodId");
        String remitter = request.getParameter("remitter");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ReceivementEntity> receivementEntities = receivementBiz.getList(ids, companyId, receivementTypeId, remitterMethodId, remitter, startDate, endDate);
        if (CollectionUtils.isEmpty(receivementEntities)) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            result.put("data", receivementEntities);
            return result;
        }
        List<Integer> receivementIds = receivementEntities.stream().map(ReceivementEntity::getId).collect(Collectors.toList());
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(receivementIds);
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

        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", receivementViewList);
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
        result.put("code", HttpStatus.OK.value());
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
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    public JSONObject delete(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        int count = receivementBiz.updateReceivementState(id, 5);
        if (count == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 做账
     * @param request
     * @return
     */
    @RequestMapping("/accounting")
    public JSONObject accounting(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        Integer receivementId = Integer.valueOf(request.getParameter("receivementId"));
        String voucherNo = request.getParameter("voucherNo");
        // TODO 校验是否可以做账
        ReceivementEntity receivementEntity = receivementBiz.getById(receivementId);
        BigDecimal diff = receivementEntity.getReceivementMoney();
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(new ArrayList<>(receivementId));
        for (SubscriptionLogEntity entity : subscriptionLogEntities) {
            diff.subtract(entity.getReceivementMoney());
        }
        if (diff.compareTo(BigDecimal.ZERO) != 0) {
            result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("msg", "账目不平，无法做账");
            return result;
        }
        AccountingLogDto accountingLogDto = new AccountingLogDto();
        accountingLogDto.setReceivementId(receivementId);
        accountingLogDto.setVoucherNo(voucherNo);
        accountingLogDto.setState(0);
        accountingLogDto.setRemark("");
        accountingLogDto.setCreateUser(userId);
        accountingLogDto.setCtime(new Date());
        int count = accountingLogBiz.addAccountingLog(accountingLogDto);
        if (count == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }
}
