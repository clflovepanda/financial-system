package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.user.biz.PermissionBiz;
import com.pro.financial.user.dto.PermissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Permission;
import java.util.ArrayList;
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
    @RequestMapping("/listlevel")
    public JSONObject listlevel(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        List<PermissionDto> permissionDtos = permissionBiz.getFirstPermissionList();
        List<PermissionDto> permissionDtoAll = permissionBiz.getOtherPermissionList();
        for (PermissionDto permissionDto : permissionDtoAll) {
            for (PermissionDto permissionDto2 : permissionDtoAll) {
                if (permissionDto.getPermissionId() - permissionDto2.getParentId() == 0) {
                    if (CollectionUtils.isEmpty(permissionDto.getPermissionSons())) {
                        List<PermissionDto> permissionsons = new ArrayList<>();
                        permissionsons.add(permissionDto2);
                        permissionDto.setPermissionSons(permissionsons);
                    } else {
                        permissionDto.getPermissionSons().add(permissionDto2);
                    }
                }
            }
        }
        for (PermissionDto permissionDto : permissionDtos) {
            List<PermissionDto> permissionDtoSons = new ArrayList<>();
            for (PermissionDto permissionDtoSon : permissionDtoAll) {
                if (permissionDtoSon.getParentId() - permissionDto.getPermissionId() == 0) {
                    permissionDtoSons.add(permissionDtoSon);
                }

            }
            permissionDto.setPermissionSons(permissionDtoSons);
        }

        result.put("code", 0);
        result.put("msg", "");
        result.put("data", permissionDtos);
        return result;
    }
}
