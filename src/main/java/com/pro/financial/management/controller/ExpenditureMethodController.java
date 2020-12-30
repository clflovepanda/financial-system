package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ExpenditureMethodBiz;
import com.pro.financial.management.dao.entity.ExpenditureMethodEntity;
import com.pro.financial.management.dto.ExpenditureMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/expenditure_method")
public class ExpenditureMethodController {

    @Autowired
    private ExpenditureMethodBiz expenditureMethodBiz;

    @RequestMapping("/add")
    public JSONObject addExpenditureMethod(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ExpenditureMethodDto expenditureMethodDto = JSONObject.parseObject(jsonInfo.toJSONString(), ExpenditureMethodDto.class);
        int count = expenditureMethodBiz.addExpenditureMethod(expenditureMethodDto);
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看支出方式列表
     */
    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<ExpenditureMethodEntity> expenditureMethodEntities = expenditureMethodBiz.getList();
        result.put("code", 0);
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("list", expenditureMethodEntities);
        return result;
    }
}
