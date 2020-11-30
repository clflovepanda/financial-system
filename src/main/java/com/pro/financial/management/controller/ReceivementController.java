package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivementBiz;
import com.pro.financial.management.biz.ReceivementTypeBiz;
import com.pro.financial.management.biz.RemitterMethodBiz;
import com.pro.financial.management.biz.SubscriptionLogBiz;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.ReceivementDto;
import com.pro.financial.user.dao.entity.CompanyEntity;
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

    @RequestMapping("/add")
    public JSONObject addReceivement(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ReceivementDto receivementDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivementDto.class);
        receivementDto.setCreateUser(userId);
        receivementDto.setCtime(new Date());
        receivementDto.setUpdateUser(userId);
        receivementDto.setUtime(new Date());
        receivementDto.setState(0);
        int count = receivementBiz.addReceivement(receivementDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // TODO 根据用户权限获取到能看到的到款id列表
        List<Integer> ids = new ArrayList<>();
        List<ReceivementEntity> receivementEntities = receivementBiz.getListById(ids);
        List<Integer> receivementIds = receivementEntities.stream().map(ReceivementEntity::getId).collect(Collectors.toList());
        // TODO 公司列表查询
        List<CompanyEntity> companyEntities = new ArrayList<>();
        List<ReceivementTypeEntity> receivementTypeEntities = receivementTypeBiz.getList();
        List<RemitterMethodEntity> remitterMethodEntities = remitterMethodBiz.getList();
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(receivementIds);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("receivementEntities", receivementEntities);
        result.put("companyEntities", companyEntities);
        result.put("receivementTypeEntities", receivementTypeEntities);
        result.put("remitterMethodEntities", remitterMethodEntities);
        result.put("subscriptionLogEntities", subscriptionLogEntities);
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
}
