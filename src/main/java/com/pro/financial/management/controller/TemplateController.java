package com.pro.financial.management.controller;


import com.alibaba.fastjson.JSONObject;
import com.pro.financial.management.biz.TemplateBiz;
import com.pro.financial.management.dto.TemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 工时模板表 前端控制器
 * </p>
 *
 * @author panda
 * @since 2020-11-23
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateBiz templateBiz;

    @RequestMapping("/add")
    public JSONObject addTemplate(@RequestBody TemplateDto templateDto) {
        JSONObject result = new JSONObject();
        templateBiz.addTemplate(templateDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/update")
    public JSONObject updateTemplate(@RequestBody TemplateDto templateDto) {
        JSONObject result = new JSONObject();
        if (templateDto.getTemplateId() == null || templateDto.getTemplateId() < 0) {
            result.put("code", 1001);
            result.put("msg", "未传入模板ID");
            return result;
        }
        templateBiz.updateTemplate(templateDto);
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/del")
    public JSONObject delTemplate(@RequestBody TemplateDto templateDto) {
        JSONObject result = new JSONObject();
        if (templateDto.getTemplateId() == null || templateDto.getTemplateId() < 0) {
            result.put("code", 1001);
            result.put("msg", "未传入模板ID");
            return result;
        }
        templateBiz.delTemplate(templateDto.getTemplateId());
        result.put("code", 0);
        result.put("msg", "");
        return result;
    }

    @RequestMapping("/getthreelayers")
    public JSONObject getThreeLayers(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        int layer = jsonInfo.getInteger("layer");
        if (layer < 1) {
            result.put("code", 1001);
            result.put("msg", "未选择第几套模板");
            return result;
        }
        TemplateDto template = templateBiz.getThreeLayers(layer);
        result.put("code", 0);
        result.put("msg", "");
        result.put("data", template);
        return result;
    }

    @RequestMapping("/getbyparentid")
    public JSONObject getByParentId(@RequestBody JSONObject jsonInfo) {
        JSONObject result = new JSONObject();
        Integer parentId = jsonInfo.getInteger("parentId");
        if (parentId == null || parentId < 1) {
            result.put("code", 1001);
            result.put("msg", "未传入id");
            return result;
        }
        List<TemplateDto> templateDtos = templateBiz.getByParentId(parentId);
        return result;
    }

}
