package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;

/**
 * Created by lala on 2018/5/31.
 */
public interface ChapterService {
    void inert(Chapter chapter);
    List<Chapter> queryByPid(String pid);

}
