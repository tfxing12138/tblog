package com.link.tblog.controller;

import com.link.tblog.entity.ResponseInfo;
import com.link.tblog.entity.TblogArticle;
import com.link.tblog.entity.dto.TblogArticlePageDto;
import com.link.tblog.service.TblogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class TblogArticleController {

    @Autowired
    private TblogArticleService tblogArticleService;

    @PostMapping("/save")
    public ResponseInfo<?> saveArticle(@RequestBody TblogArticle dto) {
        return ResponseInfo.success(tblogArticleService.saveArticle(dto));
    }

    @PostMapping("/page")
    public ResponseInfo<?> pageArticle(@RequestBody TblogArticlePageDto dto) {
        return ResponseInfo.success(tblogArticleService.pageArticle(dto));
    }

    @GetMapping("/getById")
    public ResponseInfo<?> getArticleById(@RequestParam("id") Integer id) {
        return ResponseInfo.success(tblogArticleService.getArticleById(id));
    }
}
