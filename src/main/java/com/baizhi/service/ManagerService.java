package com.baizhi.service;

import com.baizhi.entity.Manager;

/**
 * Created by lala on 2018/5/28.
 */
public interface ManagerService {
    Manager login(Manager manager);
    void updatePassword(String id,String password);
}
