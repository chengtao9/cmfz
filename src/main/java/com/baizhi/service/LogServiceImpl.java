package com.baizhi.service;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lala on 2018/6/5.
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    public LogMapper getLogMapper() {
        return logMapper;
    }

    public void setLogMapper(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Log> queryAll(Integer begin,Integer rows) {
        return logMapper.queryAll(begin,rows);
    }
}
