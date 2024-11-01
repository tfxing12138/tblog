package com.link.tblog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.link.tblog.entity.TblogArticle;
import com.link.tblog.entity.dto.TblogArticlePageDto;
import com.link.tblog.mapper.TblogArticleMapper;
import com.link.tblog.service.TblogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TblogArticleServiceImpl implements TblogArticleService {

    @Autowired
    private TblogArticleMapper tblogArticleDao;

    @Override
    public Integer saveArticle(TblogArticle dto) {
        return tblogArticleDao.insert(dto);
    }

    @Override
    public IPage<TblogArticle> pageArticle(TblogArticlePageDto dto) {
        Page<TblogArticle> page = new Page<TblogArticle>(dto.getCurrentPage(),dto.getPageSize());
        Page<TblogArticle> pageInfo = tblogArticleDao.selectPage(page, null);
        return pageInfo;
    }

    @Override
    public TblogArticle getArticleById(Integer id) {
        return tblogArticleDao.selectById(id);
    }
}
