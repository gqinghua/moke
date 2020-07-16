package com.moke.cms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moke.common.entity.cms.CmsHelp;
import com.moke.common.entity.cms.CmsHelpCategory;
import com.moke.cms.mapper.CmsHelpCategoryMapper;
import com.moke.cms.mapper.CmsHelpMapper;
import com.moke.cms.service.ICmsHelpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 帮助表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class CmsHelpServiceImpl extends ServiceImpl<CmsHelpMapper, CmsHelp> implements ICmsHelpService {

    @Resource
    private CmsHelpMapper helpMapper;
    @Resource
    private CmsHelpCategoryMapper helpCategoryMapper;

    @Override
    @Transactional
    public boolean saves(CmsHelp entity) {
        entity.setCreateTime(new Date());
        helpMapper.insert(entity);
        CmsHelpCategory category = helpCategoryMapper.selectById(entity.getCategoryId());
        category.setHelpCount(category.getHelpCount() + 1);
        helpCategoryMapper.updateById(category);
        return true;
    }
}
