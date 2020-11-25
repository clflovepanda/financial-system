package com.pro.financial.management.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.financial.management.converter.TemplateDto2Entity;
import com.pro.financial.management.converter.TemplateEntity2Dto;
import com.pro.financial.management.dao.TemplateDao;
import com.pro.financial.management.dao.entity.TemplateEntity;
import com.pro.financial.management.dto.TemplateDto;
import com.pro.financial.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 工时模板表 服务实现类
 * </p>
 *
 * @author panda
 * @since 2020-11-23
 */
@Service
public class TemplateBiz extends ServiceImpl<TemplateDao, TemplateEntity> {

    @Autowired
    private TemplateDao templateDao;


    public int addTemplate(TemplateDto templateDto) {
        TemplateEntity templateEntity = TemplateDto2Entity.instance.convert(templateDto);
        return templateDao.insert(templateEntity);
    }

    public int updateTemplate(TemplateDto templateDto) {
        TemplateEntity templateEntity = TemplateDto2Entity.instance.convert(templateDto);
        return templateDao.updateById(templateEntity);
    }

    public int delTemplate(Integer templateId) {
        return templateDao.deleteById(templateId);
    }

    public TemplateDto getThreeLayers(int layer) {
        QueryWrapper<TemplateEntity> level1wrapper = new QueryWrapper();
        level1wrapper.eq("level", 1).eq("parent_id", 0).eq("sets", layer);
        TemplateDto template = new TemplateDto();
        //查出1级模板
        List<TemplateEntity> templateLevel1 = templateDao.selectList(level1wrapper);

        return null;
    }

    public List<TemplateDto> getByParentId(Integer parentId) {
        QueryWrapper<TemplateEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<TemplateEntity> templateEntities = templateDao.selectList(wrapper);
        return ConvertUtil.convert(TemplateEntity2Dto.instance, templateEntities);
    }
}
