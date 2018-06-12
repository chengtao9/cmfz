package com.baizhi.controller;

import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by lala on 2018/6/5.
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
    @RequestMapping("/show")
    public List<Log> getLogAll(Integer page,Integer rows){
        Integer begin=(page-1)*rows;
        return logService.queryAll(begin,rows);

    }
}
