package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.ValidateCodeConst;
import com.pro.financial.user.biz.LoginBiz;
import com.pro.financial.user.biz.ValidateBiz;
import com.pro.financial.user.dto.ValidateDto;
import com.pro.financial.utils.ImageUtil;
import com.pro.financial.utils.VerifyCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;
    @Autowired
    private ValidateBiz validateBiz;

    @RequestMapping("/check")
    public JSONObject check(@RequestBody JSONObject jsonInfo, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String userName = jsonInfo.getString("userName");
        String password = jsonInfo.getString("password");
        Integer validateCode = jsonInfo.getInteger("validate");
        String code = jsonInfo.getString("code");
        return loginBiz.login(userName, password, validateCode, code, request, response);
    }
    /**
     * 响应图形验证码页面
     * @return
     */
    @RequestMapping(value="/createValidateCode", method = RequestMethod.GET)
    public JSONObject createValidateCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        VerifyCodeUtils vCode = new VerifyCodeUtils(120,40,4,100);
        ValidateDto validateDto = validateBiz.addValidate(vCode);

        //封装数据
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", validateDto);
        return result;
    }

    @RequestMapping(value="/checkValidateCode", method = RequestMethod.GET)
    public JSONObject checkValidateCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String vCode = request.getParameter("vCode");
        if (userId < 1) {
            result.put("code", 1001);
            result.put("msg", "未获取到用户ID");
            return result;
        }
        String realVCode = ValidateCodeConst.vCode.get(userId);
        if (StringUtils.isEmpty(vCode) || vCode.length() != 5 || !StringUtils.equals(vCode, realVCode)) {
            result.put("code", 1001);
            result.put("msg", "验证码错误");
            return result;
        }
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }
}
