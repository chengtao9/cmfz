package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lala on 2018/5/31.
 */
public interface AlbumDao {
    Album queryById(String  id);
    List<Album> queryAll();
    void insert(Album album);
    void updateCount(Album album);
    List<Album> queryPage(@Param(value = "begin") Integer begin, @Param(value = "rows") Integer rows);

}
