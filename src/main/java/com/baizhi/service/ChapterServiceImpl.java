package com.baizhi.service;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lala on 2018/5/31.
 */
@Service
public class ChapterServiceImpl implements  ChapterService{
    @Autowired
    private ChapterDao chapterDao;

    public ChapterDao getChapterDao() {
        return chapterDao;
    }

    public void setChapterDao(ChapterDao chapterDao) {
        this.chapterDao = chapterDao;
    }

    @Override
    @LogAnnotation(name = "添加章节")
    public void inert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public List<Chapter> queryByPid(String pid) {
        return chapterDao.queryByPid(pid);
    }
}
