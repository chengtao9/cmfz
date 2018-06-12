package com.baizhi.service;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lala on 2018/5/31.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    public AlbumDao getAlbumDao() {
        return albumDao;
    }

    public void setAlbumDao(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Album queryById(String id) {

        return albumDao.queryById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> queryPage(Integer begin,Integer rows) {
        return albumDao.queryPage(begin,rows);
    }

    @Override
    @LogAnnotation(name = "添加专辑")
    public void inert(Album album) {
            albumDao.insert(album);
    }

    @Override
    @LogAnnotation(name = "修改专辑中章节的数量")
    public void update(Album album) {
        albumDao.updateCount(album);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount() {
        List<Album> albums = albumDao.queryAll();
        Integer count=0;
        for (Album album:albums
             ) {
            count+=album.getCount();
        }
        return count;
    }

    @Override
    public List<Album> queryShow() {
        List<Album> albums = albumDao.queryAll();
        Random random = new Random();
        List<Album> l=new ArrayList<Album>();
        for (int i = 0; i < 6; i++) {
            Album album = albums.get(random.nextInt(albums.size()));
            if(!l.contains(album)){
                l.add(album);
            }
        }
        return l;
    }
}
