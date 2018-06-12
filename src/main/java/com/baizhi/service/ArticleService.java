package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;

/**
 * Created by lala on 2018/6/6.
 */
public interface ArticleService {
    List<Article> queryByNewDate();
}
