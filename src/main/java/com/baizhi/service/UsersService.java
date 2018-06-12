package com.baizhi.service;

import com.baizhi.entity.Province;
import com.baizhi.entity.Users;

import java.util.List;

/**
 * Created by lala on 2018/6/3.
 */
public interface UsersService {
    List<Users> queryAll(Integer begin, Integer row);
    List<Province> queryBySex(String sex);
    Integer queryByDate(Integer day);
    List<Users> queryCount();
    void update(Users users);
    void insert(List<Users> list);
    Users queryByUsername(String username);
    int insertSelective(Users record);
    Users selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(Users record);
    List<Users> queryVIP(String uid);
}
