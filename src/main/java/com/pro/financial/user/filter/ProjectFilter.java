package com.pro.financial.user.filter;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectBiz;
import com.pro.financial.management.dao.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@Component
public class ProjectFilter implements Filter {

    @Autowired
    private ProjectBiz projectBiz;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Integer projectId = Integer.parseInt(request.getParameter("projectId") == null ? "0" : request.getParameter("projectId"));
        if (projectId < 1) {
            filterChain.doFilter(request, response);
            return;
        }
        ProjectEntity projectEntity = projectBiz.getById(projectId);
        //已经关闭
        if (projectEntity.getState() == 6) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            Writer writer = response.getWriter();
            JSONObject result = new JSONObject();
            result.put("code", 9999);
            result.put("msg", "项目已经被关闭,无法操作");
            writer.write(result.toJSONString());
        } else {
            filterChain.doFilter(request, response);
            return;
        }
    }
}
