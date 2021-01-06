package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.biz.RevenueTypeBiz;
import com.pro.financial.management.dao.entity.RevenueStatisticEntity;
import com.pro.financial.management.dto.RevenueDto;
import com.pro.financial.management.dto.RevenueTypeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/revenue")
public class RevenueController {

    @Autowired
    private RevenueBiz revenueBiz;
    @Autowired
    private RevenueTypeBiz revenueTypeBiz;

    @RequestMapping("/add")
    public JSONObject addRevenue(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        RevenueDto revenueDto = JSONObject.parseObject(jsonInfo.toJSONString(), RevenueDto.class);
        int count = revenueBiz.addRevenue(revenueDto);
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
    @RequestMapping("/gettype")
    public JSONObject getRevenueType(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWords = request.getParameter("keyWords");
        List<RevenueTypeDto> revenueTypeDtos = revenueTypeBiz.getType(keyWords);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", revenueTypeDtos);
        return result;
    }

    @RequestMapping("/list")
    public JSONObject getExpenditure(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String projectId = request.getParameter("projectId");
        //收入编号
        String revenueNo = request.getParameter("revenueNo");
        //汇款放类型
        String remitterMethodId = request.getParameter("remitterMethodId");
        //认款类型
        String receivementTypeId = request.getParameter("receivementTypeId");
        //账户
        String companyId = request.getParameter("companyId");
        //汇款方
        String remitter = request.getParameter("remitter");
        //认款人
        String createUser = request.getParameter("createUser");

        String revenueTypeId = "1";

        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit*(offset - 1);
        List<RevenueDto> revenueDtos = revenueBiz.searchList(projectId, revenueNo, remitterMethodId, receivementTypeId, companyId,
                remitter, createUser, startDate, endDate, null, null, revenueTypeId, limit, offset);
        RevenueStatisticEntity revenueStatisticEntity = revenueBiz.getStatistic(projectId, revenueNo, remitterMethodId, receivementTypeId, companyId,
                remitter, createUser, startDate, endDate, null, null, revenueTypeId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", revenueDtos);
        result.put("statistic", revenueStatisticEntity);
        return result;
    }
}
