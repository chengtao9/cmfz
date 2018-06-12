package com.baizhi.service;

import com.baizhi.entity.TitlePic;

import java.util.List;

/**
 * Created by lala on 2018/5/29.
 */
public interface TitlePicService {
    void updataStatus(String status,String id);
    void delete(String id);
    void insert(TitlePic titlePic);
    List<TitlePic> queryAll(Integer begin,Integer row);
    Integer size();
    List<TitlePic> queryStatus(String status);
}
