package com.baizhi.service;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.UsersDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Province;
import com.baizhi.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lala on 2018/6/3.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    public UsersDao getUsersDao() {
        return usersDao;
    }

    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Users> queryAll(Integer begin, Integer row) {
        return usersDao.queryAll(begin, row);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Province> queryBySex(String sex) {
        return usersDao.queryByProvince(sex);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryByDate(Integer day) {
        return usersDao.queryByDate(day);
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Users> queryCount(){
        return usersDao.queryCount();
    }
    @Override
    @LogAnnotation(name = "修改用户")
    public void update(Users users){
        usersDao.updateStatus(users);
    }
    @Override
    @LogAnnotation(name = "批量导入用户")
    public void insert(List<Users> list){
        usersDao.insert(list);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Users queryByUsername(String username) {
        return usersDao.queryByPhobeNum(username);
    }

    @Override
    public int insertSelective(Users record) {
        return usersDao.insertSelective(record);
    }

    @Override
    public Users selectByPrimaryKey(String id) {
        return usersDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Users record) {
        return usersDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Users> queryVIP(String uid) {
        List<Users> users = usersDao.queryCount();
        Random random = new Random();
        Users users1 = usersDao.selectByPrimaryKey(uid);
        List<Users> l=new ArrayList<Users>();
        for (int i = 0; i < 6; i++) {
            Users user  = users.get(random.nextInt(users.size()));
            if(!l.contains(user)||!user.equals(users1)){
                l.add(user);
            }
        }
        return l;
    }
}
