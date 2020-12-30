package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.RevenueBiz;
import com.pro.financial.management.dto.RevenueDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private RevenueBiz revenueBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String projectId = request.getParameter("projectId");
        //押金编号
        String revenueNo = request.getParameter("revenueNo");
        //项目名称
        String projectName = request.getParameter("projectName");
        //项目编号
        String projectNo = request.getParameter("projectNo");
        //到款账户
        String companyId = request.getParameter("companyId");
        //到款种类
        String receivementTypeId = request.getParameter("receivementTypeId");
        //汇款方
        String remitter = request.getParameter("remitter");
        //认款人
        String createUser = request.getParameter("createUser");
        String revenueTypeId = "";

        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        List<RevenueDto> revenueDtos = revenueBiz.searchList(projectId, revenueNo, null, receivementTypeId,
                companyId, remitter, createUser, startDate, endDate, projectName, projectNo, revenueTypeId);
        Map<String, Object> resutlMap = new HashMap<>();
        resutlMap.put("deposit", revenueDtos);
        BigDecimal toBeReturned = new BigDecimal(0);
        BigDecimal returned = new BigDecimal(0);
        BigDecimal returning = new BigDecimal(0);
        for (RevenueDto revenueDto : revenueDtos) {
            toBeReturned = toBeReturned.add(revenueDto.getToBeReturned() == null ? new BigDecimal(0) : revenueDto.getToBeReturned());
            returned = returned.add(revenueDto.getReturned() == null ? new BigDecimal(0) : revenueDto.getReturned());
            returning = returning.add(revenueDto.getReturning() == null ? new BigDecimal(0) : revenueDto.getReturning());
        }
        resutlMap.put("toBeReturned", toBeReturned);
        resutlMap.put("returned", returned);
        resutlMap.put("returning", returning);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", resutlMap);
        return result;
    }

    @RequestMapping("/audit")
    public JSONObject list(HttpServletRequest request, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();

        return result;
    }

    @RequestMapping("/revenue")
    public JSONObject revenue(HttpServletRequest request) {
        return null;
    }
}