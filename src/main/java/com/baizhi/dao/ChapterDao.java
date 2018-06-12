package com.baizhi.dao;

import com.baizhi.entity.Chapter;

import java.util.List;

/**
 * Created by lala on 2018/5/31.
 */
public interface ChapterDao {
    void insert(Chapter chapter);
    List<Chapter> queryByPid(String pid);
}
