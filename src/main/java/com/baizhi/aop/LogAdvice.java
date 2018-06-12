package com.baizhi.aop;

import com.baizhi.annonation.LogAnnotation;
import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.Manager;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lala on 2018/6/5.
 */
public class LogAdvice implements MethodInterceptor {
    @Autowired
    private LogMapper logMapper;

    public LogMapper getLogMapper() {
        return logMapper;
    }

    public void setLogMapper(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Log log = new Log();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpSession session = requestAttributes.getRequest().getSession();
        Manager manager = (Manager)session.getAttribute("manager");
        log.setUsername(manager.getUsername());
        log.setUserid(manager.getId());
        LogAnnotation annotation = methodInvocation.getMethod().getAnnotation(LogAnnotation.class);
        log.setMethod(annotation.name());
        log.setDate(new Date());

        String status="成功";
        Object proceed=null;
        try {
            proceed = methodInvocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            status="失败";
        }
        log.setStatus(status);
        log.setId(UUID.randomUUID().toString().replace("-",""));
        logMapper.insert(log);
        return proceed;
    }
}
