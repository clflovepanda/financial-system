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

    public List<TemplateDto> getThreeLayers(int layer) {
        QueryWrapper<TemplateEntity> level1wrapper = new QueryWrapper();
        level1wrapper.eq("level", 1).eq("parent_id", 0).eq("sets", layer);
        TemplateDto template = new TemplateDto();
        //查出1级模板
        List<TemplateEntity> templateLevel1List = templateDao.selectList(level1wrapper);
        if (templateLevel1List != null) {
            for (TemplateEntity templateLevel1 : templateLevel1List) {
                template = TemplateEntity2Dto.instance.convert(templateLevel1);
                //查出2级模板
                QueryWrapper<TemplateEntity> levle2wrapper = new QueryWrapper<>();
                levle2wrapper.eq("level", 2).eq("parent_id", templateLevel1.getTemplateId()).eq("origin_id", templateLevel1.getTemplateId());
                List<TemplateEntity> templateLevel2 = templateDao.selectList(levle2wrapper);
                if (!CollectionUtils.isEmpty(templateLevel2)) {
                    templateLevel1.setTemplates(templateLevel2);
                    //遍历2级模板查出3级模板
                    for (TemplateEntity templateEntity : templateLevel2) {
                        QueryWrapper<TemplateEntity> levle3wrapper = new QueryWrapper<>();
                        levle2wrapper.eq("level", 3).eq("parent_id", templateEntity.getTemplateId()).eq("origin_id", templateLevel1.getTemplateId());
                        List<TemplateEntity> templateLevel3 = templateDao.selectList(levle3wrapper);
                        templateEntity.setTemplates(templateLevel3);
                    }
                }
                template.setTemplates(ConvertUtil.convert(TemplateEntity2Dto.instance, templateLevel2));
            }

        }
        return ConvertUtil.convert(TemplateEntity2Dto.instance, templateLevel1List);
    }

    public List<TemplateDto> getByParentId(Integer parentId) {
        QueryWrapper<TemplateEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        List<TemplateEntity> templateEntities = templateDao.selectList(wrapper);
        return ConvertUtil.convert(TemplateEntity2Dto.instance, templateEntities);
    }
}
