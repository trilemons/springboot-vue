package com.lml.controller;

import com.lml.pojo.Article;
import com.lml.pojo.PageBean;
import com.lml.pojo.Result;
import com.lml.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
       PageBean<Article> pb =  articleService.list(pageNum,pageSize,categoryId,state);
       return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article> selectById(int id){
        Article article = articleService.selectById(id);
        if(article==null)
            return Result.error("该文章不存在");

        return Result.success(article);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(int id){
        Article article = articleService.selectById(id);
        if (null==article){
            return Result.error("the article you want to delete is null");
        }
        articleService.delete(id);
        return Result.success();
    }
}
