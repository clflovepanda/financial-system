package com.pro.financial.management.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/deposit")
public class DepositController {

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        String revenueNo = request.getParameter("revenueNo");
        String projectId = request.getParameter("projectId");
        String projectNo = request.getParameter("projectNo");
        String companyId = request.getParameter("companyId");
//        String revenue
        return null;
    }
}
