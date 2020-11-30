package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ReceivementBiz;
import com.pro.financial.management.dto.ReceivementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/receivement")
public class ReceivementController {

    @Autowired
    private ReceivementBiz receivementBiz;

    @RequestMapping("/add")
    public JSONObject addReceivement(@RequestBody JSONObject jsonInfo, @CookieValue("user_id") Integer userId) {
        JSONObject result = new JSONObject();
        ReceivementDto receivementDto = JSONObject.parseObject(jsonInfo.toJSONString(), ReceivementDto.class);
        receivementDto.setCreateUser(userId);
        receivementDto.setCtime(new Date());
        receivementDto.setUpdateUser(userId);
        receivementDto.setUtime(new Date());
        receivementDto.setState(0);
        int count = receivementBiz.addReceivement(receivementDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }
}
