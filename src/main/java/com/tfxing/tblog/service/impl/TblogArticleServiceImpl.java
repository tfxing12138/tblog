package com.tfxing.tblog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tfxing.tblog.entity.TblogArticle;
import com.tfxing.tblog.entity.dto.TblogArticlePageDto;
import com.tfxing.tblog.mapper.TblogArticleMapper;
import com.tfxing.tblog.service.TblogArticleService;
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
        return tblogArticleDao.selectPage(page,null);
    }

    @Override
    public TblogArticle getArticleById(Integer id) {
        return tblogArticleDao.selectById(id);
    }
}
