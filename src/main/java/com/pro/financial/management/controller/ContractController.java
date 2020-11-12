package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ContractBiz;
import com.pro.financial.management.dto.ContractDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractBiz contractBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String contractName = request.getParameter("contractName");
        String contractNo = request.getParameter("contractNo");
        Integer limit = StringUtils.isEmpty(request.getParameter("limit")) ? null : Integer.parseInt(request.getParameter("limit"));
        Integer offset = StringUtils.isEmpty(request.getParameter("offset")) ? null : Integer.parseInt(request.getParameter("offset"));
        List<ContractDto> contractDtos = contractBiz.getContarct(contractName, contractNo, limit, offset);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", contractDtos);
        return result;
    }

    @RequestMapping("/add")
    public JSONObject addContract(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ContractDto contractDto = JSONObject.parseObject(jsonInfo.toJSONString(), ContractDto.class);
        int count = contractBiz.addContract(contractDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getbyid")
    public JSONObject getContract(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int contractId = 0;
        if (StringUtils.isNumeric(request.getParameter("contractId"))) {
            contractId = Integer.parseInt(request.getParameter("contractId"));
        }
        if (contractId < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        ContractDto contractDto = contractBiz.getByContractId(contractId);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", contractDto);
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateContract(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ContractDto contractDto = JSONObject.parseObject(jsonInfo.toJSONString(), ContractDto.class);
        if (contractDto.getContractId() == null || contractDto.getContractId() < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        int count = contractBiz.updateContract(contractDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delContract(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int contractId = 0;
        if (StringUtils.isNumeric(request.getParameter("contractId"))) {
            contractId = Integer.parseInt(request.getParameter("contractId"));
        }
        if (contractId < 1) {
            result.put("code", 1001);
            result.put("msg", "合同号有误");
            return result;
        }
        contractBiz.deleteContract(contractId);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

}
