package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.SubscriptionLogBiz;
import com.pro.financial.management.dto.ProjectDto;
import com.pro.financial.management.dto.SubscriptionLogDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/subscription_log")
public class SubscriptionLogController {

    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;

    @RequestMapping("/add")
    public JSONObject addSubscriptionLog(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        SubscriptionLogDto subscriptionLogDto = JSONObject.parseObject(jsonInfo.toJSONString(), SubscriptionLogDto.class);
        int count = subscriptionLogBiz.addSubscriptionLog(subscriptionLogDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
