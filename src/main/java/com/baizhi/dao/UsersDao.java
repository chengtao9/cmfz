package com.baizhi.dao;

import com.baizhi.entity.Province;
import com.baizhi.entity.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lala on 2018/6/3.
 */
public interface UsersDao {
    List<Users> queryAll(@Param(value = "begin") Integer begin, @Param(value = "row") Integer row);
    Integer queryByDate(Integer days);
    List<Province> queryByProvince(String sex);
    void updateStatus(Users users);
    void insert(List<Users> list);
    Users queryByPhobeNum(String phoneNum);
    List<Users> queryCount();
    int insertSelective(Users record);
    int updateByPrimaryKeySelective(Users record);
    Users selectByPrimaryKey(String id);
}
