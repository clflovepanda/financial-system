package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivableBiz;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.management.dto.ReceivableDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author yupeng
 * @since 2020-11-21
 */
@RestController
@RequestMapping("/receivable")
public class ReceivableController {

    @Autowired
    private ReceivableBiz receivableBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request, @RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        String org = jsonInfo.getString("org");
        String receivableNo = jsonInfo.getString("receivableNo");
        String taxableServiceName = jsonInfo.getString("taxableServiceName");
        String userName = jsonInfo.getString("userName");
        Date startDate = jsonInfo.getDate("start");
        Date endDate = jsonInfo.getDate("end");
        Integer limit = StringUtils.isEmpty(request.getParameter("limit")) ? null : Integer.parseInt(request.getParameter("limit"));
        Integer offset = StringUtils.isEmpty(request.getParameter("offset")) ? null : Integer.parseInt(request.getParameter("offset"));
        List<ReceivableDto> receivableDtos = receivableBiz.getReceivable(org, receivableNo, taxableServiceName, userName, startDate, endDate, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", receivableDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addReceivable(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ReceivableDto receivableDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivableDto.class);
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
