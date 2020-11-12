package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ContractBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractBiz contractBiz;

    @RequestMapping("/get")
    public JSONObject getContract(HttpServletRequest request) {
        JSONObject result = new JSONObject();

        return result;
    }
}
