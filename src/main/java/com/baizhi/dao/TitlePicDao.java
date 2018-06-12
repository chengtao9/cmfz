package com.baizhi.dao;

import com.baizhi.entity.TitlePic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lala on 2018/5/29.
 */
public interface TitlePicDao {
    void updateByStatus(@Param(value = "status") String status, @Param(value = "id") String id);
    void insert(TitlePic titlePic);
    void delete(String  id);
    List<TitlePic> queryAll(@Param(value = "begin") Integer begin,@Param(value = "row") Integer row);
    List<TitlePic> querySize();
    List<TitlePic> queryStatue(String status);
}
