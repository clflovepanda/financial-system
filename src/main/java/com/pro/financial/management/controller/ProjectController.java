package com.pro.financial.management.controller;

import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.ProjectAuditLogBiz;
import com.pro.financial.management.biz.ProjectBiz;
import com.pro.financial.management.dto.ContractDto;
import com.pro.financial.management.dto.ProjectAuditLogDto;
import com.pro.financial.management.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectBiz projectBiz;
    @Autowired
    private ProjectAuditLogBiz projectAuditLogBiz;

    @RequestMapping("/add")
    public JSONObject addProject(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        ProjectDto projectDto = JSONObject.parseObject(jsonInfo.toJSONString(), ProjectDto.class);
        int count = projectBiz.addProject(projectDto);
        result.put("code", HttpStatus.OK.value());
        result.put("msg", HttpStatus.OK.getReasonPhrase());
        return result;
    }

    /**
     * 查看项目列表页
     */
    @RequestMapping("project_list")
    public JSONObject getProjectList(HttpServletRequest request) {
        return null;
    }

    /**
     * 查看项目详情/修改项目页
     */
    @RequestMapping("project_detail")
    public JSONObject getProjectDetail(HttpServletRequest request) {
        return null;
    }

    /**
     * 开启/关闭/暂停项目
     */
    @RequestMapping("project_state")
    public JSONObject getProjectClose(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer state = Integer.valueOf(request.getParameter("project_state"));
        int updateResult = projectBiz.updateState(id, state);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 销售提成已发放   1  部分发放  2 全部发放
     */
    @RequestMapping("distribute_sales_commission")
    public JSONObject distributeSalesCommission1(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer saleCommisState = Integer.valueOf(request.getParameter("sale_commis_state"));
        int updateResult = projectBiz.updateSaleCommisState(id, saleCommisState);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;
    }

    /**
     * 项目审核通过/驳回 audit_state = 1 通过   2 驳回
     */
    @RequestMapping("project_audit")
    public JSONObject approveProjectAudit(HttpServletRequest request, @CookieValue("userId") Integer userId) {
        JSONObject result = new JSONObject();
        Integer id = Integer.valueOf(request.getParameter("id"));
        Integer auditState = Integer.valueOf(request.getParameter("audit_state"));
        int updateResult = projectBiz.updateAuditState(id, auditState);
        if (updateResult == 1) {
            result.put("code", HttpStatus.OK.value());
            result.put("msg", HttpStatus.OK.getReasonPhrase());
            ProjectAuditLogDto projectAuditLogDto = new ProjectAuditLogDto();
            projectAuditLogDto.setProjectId(id);
            projectAuditLogDto.setAuditType(auditState);
            projectAuditLogDto.setCreateUser(userId);
            projectAuditLogDto.setCtime(new Date());
            projectAuditLogDto.setState(0);
            projectAuditLogBiz.addProjectAuditLog(projectAuditLogDto);
            return result;
        }
        result.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.put("msg", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return result;

    }

}
