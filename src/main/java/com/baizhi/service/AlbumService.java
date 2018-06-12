package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

/**
 * Created by lala on 2018/5/31.
 */
public interface AlbumService {
    Album queryById(String id);
    List<Album> queryPage(Integer begin,Integer rows);
    void inert(Album album);
    void update(Album album);
    Integer queryCount();
    List<Album> queryShow();
}
