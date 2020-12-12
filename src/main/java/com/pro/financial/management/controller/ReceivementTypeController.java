package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivementTypeBiz;
import com.pro.financial.management.dao.entity.ExpenditurePurposeEntity;
import com.pro.financial.management.dao.entity.ReceivementTypeEntity;
import com.pro.financial.management.dto.ReceivementTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/receivement_type")
public class ReceivementTypeController {

    @Autowired
    private ReceivementTypeBiz receivementTypeBiz;

    @RequestMapping("/add")
    public JSONObject addReceivementType(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ReceivementTypeDto receivementTypeDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivementTypeDto.class);
        int count = receivementTypeBiz.addReceivementType(receivementTypeDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看到款类型列表
     */
    @RequestMapping("/list")
    public JSONObject getList(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<ReceivementTypeEntity> receivementTypeEntities = receivementTypeBiz.getList();
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        result.put("list", receivementTypeEntities);
        return result;
    }
}
