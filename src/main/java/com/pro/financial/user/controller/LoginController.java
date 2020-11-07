package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.LoginBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;

    @RequestMapping("/check")
    public JSONObject check(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        String userName = jsonInfo.getString("userName");
        String password = jsonInfo.getString("password");
        return loginBiz.login(userName, password);
    }
}
