package com.lml.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lml.mapper.ArticleMapper;
import com.lml.pojo.Article;
import com.lml.pojo.PageBean;
import com.lml.service.ArticleService;
import com.lml.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article selectById(int id) {
        return articleMapper.selectById(id);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(int id) {
        articleMapper.deleteById(id);
    }

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
        //创建封装对象
        PageBean<Article> articlePageBean = new PageBean<>();
        Map map = (Map) ThreadLocalUtil.get();
        int userId = (int) map.get("id");
        //创建分页
        PageHelper.startPage(pageNum,pageSize);

        List<Article> list = articleMapper.list(userId, categoryId, state);

//        //Page中提供了方法，可以得到在经过PageHelper分页查询后的总条数和当前页数据
//        Page<Article> articlePage = (Page<Article>) list;

        // 获取分页信息
        PageInfo<Article> pageInfo = new PageInfo<>(list);

        articlePageBean.setTotal(pageInfo.getTotal());
        articlePageBean.setItems(pageInfo.getList());

        return articlePageBean;
    }
}
