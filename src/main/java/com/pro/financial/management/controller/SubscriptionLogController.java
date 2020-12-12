package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.SubscriptionLogBiz;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dao.entity.RemitterMethodEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.SubscriptionLogDto;
import com.pro.financial.user.dao.entity.CompanyEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subscription_log")
public class SubscriptionLogController {

    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;

    @RequestMapping("/add")
    public JSONObject addSubscriptionLog(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        SubscriptionLogDto subscriptionLogDto = JSONObject.parseObject(jsonInfo.toJSONString(), SubscriptionLogDto.class);
        int count = subscriptionLogBiz.addSubscriptionLog(subscriptionLogDto);
        // TODO 这里需要处理 如果是收入类型为预收押金，则自动进入到押金列表。收入类型为收回押金，则自动进入到支出管理-押金支出列表。
        // TODO 或者在收入支出押金列表中区别类型处理
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer receivementId = Integer.valueOf(request.getParameter("receivementId"));
        List<Integer> ids = new ArrayList<>();
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(new ArrayList<>(receivementId));
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("subscriptionLogEntities", subscriptionLogEntities);
        return result;
    }
}
