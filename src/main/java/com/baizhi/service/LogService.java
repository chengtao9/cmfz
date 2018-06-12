package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.List;

/**
 * Created by lala on 2018/6/5.
 */
public interface LogService {
    List<Log> queryAll(Integer begin,Integer rows);
}
