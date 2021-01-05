package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivableBiz;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.management.dto.ReceivableDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 应收单表 前端控制器
 * </p>
 *
 * @author panda
 * @since 2020-11-21
 */
@RestController
@RequestMapping("/receivable")
public class ReceivableController {

    @Autowired
    private ReceivableBiz receivableBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String org = request.getParameter("org");
        String receivableNo = request.getParameter("receivableNo");
        String taxableServiceName = request.getParameter("taxableServiceName");
        String userName = request.getParameter("userName");
        String startDt = request.getParameter("startDt");
        String endDt = request.getParameter("endDt");
        Date startDate = StringUtils.isEmpty(startDt) ? null : new Date(Long.parseLong(startDt));
        Date endDate = StringUtils.isEmpty(endDt) ? null : new Date(Long.parseLong(endDt));
        String projectId = request.getParameter("projectId");
        Integer limit = Integer.parseInt(StringUtils.isEmpty(request.getParameter("limit")) ? "1000" : request.getParameter("limit"));
        Integer offset = Integer.parseInt(StringUtils.isEmpty(request.getParameter("offset")) ? "1" : request.getParameter("offset"));
        offset = limit*(offset - 1);
        List<ReceivableDto> receivableDtos = receivableBiz.getReceivable(projectId, org, receivableNo, taxableServiceName, userName, startDate, endDate, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", receivableDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addReceivable(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ReceivableDto receivableDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivableDto.class);
        receivableDto.setUserId(userId);
        int count = receivableBiz.addReceivable(receivableDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateReceivable(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ReceivableDto receivableDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivableDto.class);
        if (receivableDto.getReceivableId() < 1) {
            result.put("code", 10001);
            result.put("msg", "id传入有误");
            return result;
        }
        int count = receivableBiz.updateReceivable(receivableDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delReceivable(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int receivableId = Integer.parseInt(request.getParameter("receivableId"));
        if (receivableId < 1) {
            result.put("code", 10001);
            result.put("msg", "id传入有误");
            return result;
        }
        int count = receivableBiz.delReceivable(receivableId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
