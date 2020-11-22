package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivementTypeBiz;
import com.pro.financial.management.dto.ReceivementTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receivement_type")
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
}
