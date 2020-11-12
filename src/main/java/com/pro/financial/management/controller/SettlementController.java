package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.SettlementBiz;
import com.pro.financial.management.dto.SettlementDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    private SettlementBiz settlementBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String settlementName = request.getParameter("settlementName");
        String settlementNo = request.getParameter("settlementNo");
        Integer limit = StringUtils.isEmpty(request.getParameter("limit")) ? null : Integer.parseInt(request.getParameter("limit"));
        Integer offset = StringUtils.isEmpty(request.getParameter("offset")) ? null : Integer.parseInt(request.getParameter("offset"));
        List<SettlementDto> settlementDtos = settlementBiz.getContarct(settlementName, settlementNo, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", settlementDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addSettlement(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        SettlementDto settlementDto = JSONObject.parseObject(jsonInfo.toJSONString(), SettlementDto.class);
        //TODO 会有条件限制
        int count = settlementBiz.addSettlement(settlementDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getbyid")
    public JSONObject getSettlement(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int settlementId = 0;
        if (StringUtils.isNumeric(request.getParameter("settlementId"))) {
            settlementId = Integer.parseInt(request.getParameter("settlementId"));
        }
        if (settlementId < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        SettlementDto settlementDto = settlementBiz.getBySettlementId(settlementId);
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateSettlement(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        SettlementDto settlementDto = JSONObject.parseObject(jsonInfo.toJSONString(), SettlementDto.class);
        if (settlementDto.getSettlementId() == null || settlementDto.getSettlementId() < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        int count = settlementBiz.updateSettlement(settlementDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delSettlement(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int settlementId = 0;
        if (StringUtils.isNumeric(request.getParameter("settlementId"))) {
            settlementId = Integer.parseInt(request.getParameter("settlementId"));
        }
        if (settlementId < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        settlementBiz.deleteSettlement(settlementId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

}
