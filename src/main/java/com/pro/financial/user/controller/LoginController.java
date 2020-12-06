package com.pro.financial.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.ValidateCodeConst;
import com.pro.financial.user.biz.LoginBiz;
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

    @RequestMapping("/check")
    public JSONObject check(@RequestBody JSONObject jsonInfo, HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String userName = jsonInfo.getString("userName");
        String password = jsonInfo.getString("password");
        return loginBiz.login(userName, password, request, response);
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
        byte[] buff = ImageUtil.imageToBytes(vCode.getBuffImg(), "png");
        //生成图片验证码并将其放入缓存中
        //TODO
//        String uuid = IdGen.uuid();
//        JedisUtils.set(uuid, vCode.getCode(), 10*60);
        //将图片转换陈字符串给前端
//        Base64 base64 = new Base64();
//        String encode = base64.encodeAsString(buff);
        String encode = Base64.getEncoder().encodeToString(buff);
        //封装数据
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", "data:image/png;base64,"+encode);
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
