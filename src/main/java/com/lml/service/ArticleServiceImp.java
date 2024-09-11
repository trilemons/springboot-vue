package com.lml.service;

import com.lml.mapper.ArticleMapper;
import com.lml.pojo.Article;
import com.lml.pojo.PageBean;
import com.lml.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ArticleServiceImp implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map map = (Map) ThreadLocalUtil.get();
        int id = (int) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);

    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        return null;
    }
}
