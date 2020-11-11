package com.pro.financial.user.filter;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pro.financial.consts.CommonConst;
import com.pro.financial.user.dto.PermissionDto;
import com.pro.financial.user.dto.UserDto;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.List;


@Component
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化filter.......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        UserDto userDto = null;
        String permissionCookieName = null;
        String datasourceCookieName = null;
        String permissionJsonStr = null;
        String datasourceJsonStr = null;
        String urlPath = request.getServletPath();
        //登录等不拦截
//        if (urlPath.indexOf("/login") != -1) {
        if (true) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute(CommonConst.cookie_user_head) != null) {
            userDto = JSONObject.parseObject(request.getSession().getAttribute(CommonConst.cookie_user_head).toString(), UserDto.class);
        } else {
            if (ArrayUtils.isNotEmpty(cookies)) {
                //有cookies
                for (Cookie cookie : cookies) {
                    if (StringUtils.equals(cookie.getName(), CommonConst.cookie_user_head)) {
                        //获取用户信息
                        userDto = JSONObject.parseObject(cookie.getValue(), UserDto.class);
                    }
                }
            }
        }
        if (userDto != null && userDto.getUserId() > 0) {
            permissionCookieName = CommonConst.cookie_permission_head + userDto.getUserId();
            datasourceCookieName = CommonConst.cookie_datasource_head + userDto.getUserId();
        }
        if (ArrayUtils.isNotEmpty(cookies)) {
            //有cookies
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), permissionCookieName)) {
                    permissionJsonStr = URLDecoder.decode(cookie.getValue(), "utf-8");
                }
                if (StringUtils.equals(cookie.getName(), datasourceCookieName)) {
                    datasourceJsonStr = URLDecoder.decode(cookie.getValue(), "utf-8");
                }
            }
        }
        if (permissionJsonStr != null) {
            List<PermissionDto> permissionDtos = JSONArray.parseArray(permissionJsonStr, PermissionDto.class);
            for (PermissionDto permissionDto : permissionDtos) {
                if (StringUtils.equals(urlPath, permissionDto.getUri())) {
                    filterChain.doFilter(request, servletResponse);
                    return;
                }
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        Writer writer = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("code", 9999);
        result.put("msg", "无权限访问");
        writer.write(result.toJSONString());
    }

    @Override
    public void destroy() {

    }
}