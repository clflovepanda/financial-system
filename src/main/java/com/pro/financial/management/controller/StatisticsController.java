package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivementBiz;
import com.pro.financial.management.biz.SubscriptionLogBiz;
import com.pro.financial.management.controller.view.ReceivementStatisticsView;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private ReceivementBiz receivementBiz;

    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;

    @RequestMapping("/receivement")
    public JSONObject receivement(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 统计方式
        int staType = Integer.valueOf(request.getParameter("staType"));
        // 年
        int year = Integer.valueOf(request.getParameter("year") == null ? "0" : request.getParameter("year"));
        // 季度
        int quarter = Integer.valueOf(request.getParameter("quarter") == null ? "0" : request.getParameter("quarter"));
        // 月
        int month = Integer.valueOf(request.getParameter("month") == null ? "0" : request.getParameter("month"));
        List<ReceivementStatisticsView> views = new ArrayList<>();
        List<ReceivementEntity> receivementEntities = receivementBiz.getAllList();

        receivementEntities = receivementEntities.stream()
                .filter(et -> year == 0 ? true : year == et.getReceiveDate().getYear())
                .filter(et -> quarter == 0 ? true : quarter == getQuarter(et.getReceiveDate()))
                .filter(et -> quarter == 0 ? true : quarter == et.getReceiveDate().getMonth())
                .collect(Collectors.toList());

        List<Integer> receivementIds = receivementEntities.stream().map(ReceivementEntity::getId).collect(Collectors.toList());
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIds(receivementIds);
        subscriptionLogEntities = subscriptionLogEntities.stream().filter(et -> et.getState() == 0).collect(Collectors.toList());
        Map<Integer, ReceivementStatisticsView> map = new HashMap<>();
        for (ReceivementEntity entity : receivementEntities) {
            int y = entity.getReceiveDate().getYear();
            if (map.containsKey(y)) {

            } else {

            }
        }

        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", views);
        return result;
    }

    private static int getQuarter(Date date) {
        int month = date.getMonth();
        switch (month) {
            case 0:
            case 1:
            case 2:
                return 1;
            case 3:
            case 4:
            case 5:
                return 2;
            case 6:
            case 7:
            case 8:
                return 3;
            case 9:
            case 10:
            case 11:
                return 4;
        }
        return 0;
    }
}
