package com.baizhi.service;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.ManagerDao;
import com.baizhi.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lala on 2018/5/28.
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    private ManagerDao managerDao;

    public ManagerDao getManagerDao() {
        return managerDao;
    }

    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Manager login(Manager manager) {
        return managerDao.queryByUsername(manager.getUsername());
    }
    @Override
    public void updatePassword(String id,String password){
        managerDao.updateById(id,password);
    }

}
