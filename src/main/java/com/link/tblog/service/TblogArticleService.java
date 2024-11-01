package com.link.tblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.link.tblog.entity.TblogArticle;
import com.link.tblog.entity.dto.TblogArticlePageDto;

public interface TblogArticleService {
    Integer saveArticle(TblogArticle dto);

    IPage<TblogArticle> pageArticle(TblogArticlePageDto dto);

    TblogArticle getArticleById(Integer id);
}
