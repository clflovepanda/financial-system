package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.PermissionBiz;
import com.pro.financial.user.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBiz permissionBiz;

    @RequestMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String keyWords = request.getParameter("keywords");
        List<PermissionDto> permissionDtos = permissionBiz.getPermissionList(keyWords);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", permissionDtos);
        return result;
    }
}
