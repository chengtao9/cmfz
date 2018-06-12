package com.baizhi.dao;

import com.baizhi.entity.Manager;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lala on 2018/5/28.
 */
public interface ManagerDao {
    Manager queryByUsername(String username);
    void updateById(@Param("id") String id,@Param("password") String password);
}
