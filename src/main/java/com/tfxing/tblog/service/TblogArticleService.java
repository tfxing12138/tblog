package com.tfxing.tblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tfxing.tblog.entity.TblogArticle;
import com.tfxing.tblog.entity.dto.TblogArticlePageDto;

public interface TblogArticleService {
    Integer saveArticle(TblogArticle dto);

    IPage<TblogArticle> pageArticle(TblogArticlePageDto dto);

    TblogArticle getArticleById(Integer id);
}
