package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureBiz;
import com.pro.financial.management.biz.ProjectBiz;
import com.pro.financial.management.biz.ReceivementBiz;
import com.pro.financial.management.biz.SubscriptionLogBiz;
import com.pro.financial.management.controller.view.ReceivementStatisticsView;
import com.pro.financial.management.dao.entity.ProjectEntity;
import com.pro.financial.management.dao.entity.ReceivementEntity;
import com.pro.financial.management.dao.entity.SubscriptionLogEntity;
import com.pro.financial.management.dto.ExpenditureDto;
import com.pro.financial.management.dto.ProjectDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private ReceivementBiz receivementBiz;

    @Autowired
    private SubscriptionLogBiz subscriptionLogBiz;

    @Autowired
    private ExpenditureBiz expenditureBiz;
    @Autowired
    private ProjectBiz projectBiz;

    /**
     * 收款统计
     * @param request
     * @return
     */
    @RequestMapping("/receivement")
    public JSONObject receivement(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        // 统计方式
        int staType = Integer.valueOf(StringUtils.isEmpty(request.getParameter("staType")) ? "0" : request.getParameter("staType"));
        // 年
        int year = Integer.valueOf(StringUtils.isEmpty(request.getParameter("year")) ? "0" : request.getParameter("year"));
        // 季度
        int quarter = Integer.valueOf(StringUtils.isEmpty(request.getParameter("quarter")) ? "0" : request.getParameter("quarter"));
        // 月
        int month = Integer.valueOf(StringUtils.isEmpty(request.getParameter("month")) ? "0" : request.getParameter("month"));

        String dataSourceId = request.getParameter("dataSourceId");
        String revenueTypeId = request.getParameter("revenueTypeId");
        String projectName = request.getParameter("projectName");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<ReceivementStatisticsView> views = new ArrayList<>();

        List<ReceivementEntity> receivementEntities = receivementBiz.statistics(dataSourceId, revenueTypeId, projectName, startDate, endDate);
        SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthformat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        receivementEntities = receivementEntities.stream()
                .filter(et -> year == 0 ? true : year == Integer.parseInt(yearformat.format(et.getReceiveDate())))
                .filter(et -> quarter == 0 ? true : quarter == getQuarter(et.getReceiveDate()))
                .filter(et -> month == 0 ? true : month == Integer.parseInt(monthformat.format(et.getReceiveDate())))
                .collect(Collectors.toList());

        List<Integer> receivementIds = receivementEntities.stream().map(ReceivementEntity::getId).collect(Collectors.toList());
        List<SubscriptionLogEntity> subscriptionLogEntities = subscriptionLogBiz.getListByReceivementIdsnew(receivementIds, projectName, dataSourceId, revenueTypeId);
        subscriptionLogEntities = subscriptionLogEntities.stream().filter(et -> et.getState() == 1).collect(Collectors.toList());
        Map<Integer, ReceivementStatisticsView> map = new HashMap<>();
        //设置到款金额和数量
        for (ReceivementEntity entity : receivementEntities) {
            int y = staType == 0 ? Integer.parseInt(yearformat.format(entity.getReceiveDate())) : staType == 1 ?
                    Integer.parseInt(yearformat.format(entity.getReceiveDate()))*100 + getQuarter(entity.getReceiveDate()) : Integer.parseInt(yearformat.format(entity.getReceiveDate()))*10000 + getQuarter(entity.getReceiveDate())*100 + Integer.parseInt(monthformat.format(entity.getReceiveDate()));
            if (map.containsKey(y)) {
                ReceivementStatisticsView viewTemp = map.get(y);
                viewTemp.setMoney(viewTemp.getMoney().add(entity.getReceivementMoney()));
                viewTemp.setCount(viewTemp.getCount() + 1);
            } else {
                ReceivementStatisticsView view = new ReceivementStatisticsView();
                view.setMoney(entity.getReceivementMoney());
                view.setCount(view.getCount() + 1);
                map.put(y, view);
            }
        }
        //添加认款押金和收入
        if (!CollectionUtils.isEmpty(subscriptionLogEntities)) {
            for (SubscriptionLogEntity entity : subscriptionLogEntities) {
                int y = staType == 0 ? Integer.parseInt(yearformat.format(entity.getSubscriptionDate())) : staType == 1 ?
                        Integer.parseInt(yearformat.format(entity.getSubscriptionDate()))*100 + getQuarter(entity.getSubscriptionDate()) : Integer.parseInt(yearformat.format(entity.getSubscriptionDate()))*10000 + getQuarter(entity.getSubscriptionDate())*100 + Integer.parseInt(monthformat.format(entity.getSubscriptionDate()));

                if (!map.containsKey(y)) {
                    continue;
                }
                ReceivementStatisticsView viewTemp = map.get(y);
                if (StringUtils.equalsIgnoreCase("Y", entity.getRevenueRemark())) {
                    viewTemp.setDeposit(viewTemp.getDeposit().add(entity.getReceivementMoney()));
                } else {
                    viewTemp.setRevenue(viewTemp.getRevenue().add(entity.getReceivementMoney()));
                }
            }
        }

        Iterator iterator = map.entrySet().iterator();
        //比较前一年设置环比金额 增长速度  发展速度 百分比
        while (iterator.hasNext()) {
            Map.Entry<Integer, ReceivementStatisticsView> entry = (Map.Entry<Integer, ReceivementStatisticsView>)iterator.next();
            int y = entry.getKey();
            ReceivementStatisticsView view = entry.getValue();
            int ytemp = y - 1;
            ReceivementStatisticsView viewTemp = map.get(ytemp);
            if (viewTemp == null) {
                continue;
            }
            view.setChainMoney(view.getMoney().subtract(viewTemp.getMoney()));
            view.setChainGrowth(view.getChainMoney().divide(viewTemp.getMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10000")).intValue());
            view.setChainDevelopment(view.getChainGrowth() + 10000);
            view.setPercentYear(10000);
            view.setPercentTotal(view.getChainMoney().divide(view.getMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10000")).intValue());
        }
        Iterator iterator1 = map.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<Integer, ReceivementStatisticsView> entry = (Map.Entry<Integer, ReceivementStatisticsView>)iterator1.next();
            int key = entry.getKey();
            int y = 0;
            int q = 0;
            int m = 0;
            //按年
            if (staType == 0) {
                y = key;
            }
            //按季节
            if (staType == 1) {
                y = key/100;
                q = key%100;
            }
            //按月
            if (staType == 2) {
                y = key/10000;
                q = key%10000/100;
                m = key%100;
            }
            entry.getValue().setYear(y);
            entry.getValue().setQuarter(q);
            entry.getValue().setMonth(m);
            views.add(entry.getValue());
        }



        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("data", views);
        return result;
    }

    private static int getQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
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

    /**
     * 支出统计
     * @param request
     * @return
     */
    @RequestMapping("/expenditure")
    public JSONObject statistics(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        //属性
        String attribute = request.getParameter("attribute");
        String company = request.getParameter("company");
        String projectNo = request.getParameter("projectNo");
        String applyUser = request.getParameter("applyUser");
        //用途
        String purpose = request.getParameter("purpose");
        String state = request.getParameter("state");
        //收款单位
        String beneficiaryUnit = request.getParameter("beneficiaryUnit");
        String startDt = request.getParameter("startDt");
        String entDt = request.getParameter("entDt");
        Date startDate = null;
        Date endDate = null;
        if (StringUtils.isNotEmpty(startDt) && StringUtils.isNotEmpty(entDt)) {
            startDate = new Date(Long.parseLong(startDt));
            endDate = new Date(Long.parseLong(entDt));
        }

        List<ExpenditureDto> expenditureDtos = expenditureBiz.statistics(attribute, company, projectNo, applyUser, purpose, state, beneficiaryUnit, startDate, endDate);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", expenditureDtos);
        return result;
    }


    /**
     * 支出统计
     * @param request
     * @return
     */
    @RequestMapping("/project")
    public JSONObject project(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String dataSourceId = request.getParameter("dataSourceId");
        String keyWord = request.getParameter("keyword");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        String state = request.getParameter("state");
        List<ProjectDto> projectDtos = projectBiz.statistics(dataSourceId, keyWord, startDate, endDate, state);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("projcet", projectDtos);

        //报价收入
        BigDecimal estincome = new BigDecimal(0);
        //结算收入
        BigDecimal settlementIncome = new BigDecimal(0);
        //实际收入
        BigDecimal relRevenue = new BigDecimal(0);
        //应收收入
        BigDecimal receivable = new BigDecimal(0);
        for (ProjectDto projectDto : projectDtos) {
            estincome = estincome.add(projectDto.getEstincome() == null ? new BigDecimal(0) : projectDto.getEstincome());
        }
        resultMap.put("estincome",estincome);
        resultMap.put("settlementIncome",settlementIncome);
        resultMap.put("relRevenue",relRevenue);
        resultMap.put("receivable",receivable);


        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resultMap);
        return result;
    }

    @RequestMapping("/receivementnew")
    public JSONObject receivementnew(HttpServletRequest request) {
        Integer companyId = Integer.valueOf(request.getParameter("companyId") == null ? "0" : request.getParameter("companyId"));
        int staType = Integer.valueOf(request.getParameter("staType"));
        // 年
        int year = Integer.valueOf(request.getParameter("year") == null ? "0" : request.getParameter("year"));
        // 季度
        int quarter = Integer.valueOf(request.getParameter("quarter") == null ? "0" : request.getParameter("quarter"));
        // 月
        int month = Integer.valueOf(request.getParameter("month") == null ? "0" : request.getParameter("month"));
        return null;
    }

}
