package com.baizhi.service;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.TitlePicDao;
import com.baizhi.entity.TitlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lala on 2018/5/29.
 */
@Service
@Transactional
public class TitlePicServiceImpl implements TitlePicService {
    @Autowired
    private TitlePicDao titlePicDao;

    public TitlePicDao getTitlePicDao() {
        return titlePicDao;
    }

    public void setTitlePicDao(TitlePicDao titlePicDao) {
        this.titlePicDao = titlePicDao;
    }

    @Override
    @LogAnnotation(name = "修改轮播图展示状态")
    public void updataStatus(String status,String id) { titlePicDao.updateByStatus(status,id);
    }

    @Override
    @LogAnnotation(name = "删除轮播图")
    public void delete(String id) {
        titlePicDao.delete(id);
    }

    @Override
    @LogAnnotation(name = "添加轮播图")
    public void insert(TitlePic titlePic) {
        titlePicDao.insert(titlePic);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<TitlePic> queryAll(Integer begin,Integer row) {
        return titlePicDao.queryAll(begin,row);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer size() {
        return titlePicDao.querySize().size();
    }

    @Override
    public List<TitlePic> queryStatus(String status) {
        List<TitlePic> titlePics = titlePicDao.queryStatue(status);
        Random random = new Random();
        List<TitlePic> l=new ArrayList<TitlePic>();
        for (int i = 0; i < 5; i++) {
            TitlePic titlePic = titlePics.get(random.nextInt(titlePics.size()));
                if(!l.contains(titlePic)){
                    l.add(titlePic);
                }
        }
        return l;
    }
}
