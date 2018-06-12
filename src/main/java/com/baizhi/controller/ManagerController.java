package com.baizhi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.baizhi.entity.Manager;
import com.baizhi.service.ManagerService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by lala on 2018/5/28.
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private Producer captchaProducer;

    public ManagerService getManagerService() {
        return managerService;
    }

    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }
    @RequestMapping("/login")
    public String login(Manager manager,HttpSession session){
        Manager manager1 = managerService.login(manager);
        if(manager1.getPassword().equals(manager.getPassword())){
           session.setAttribute("manager",manager1);
            return "main/main";
        }else{
            return "login";
        }
    }
    public String update(String password){
        return null;
    }
    @RequestMapping(value = "captcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
    @RequestMapping("checkCode")
    public void checkCode(String code, HttpSession session, PrintWriter pw){
        String ycode= (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
          String toJson=null;
        if(code.equals(ycode)){
            toJson= JSON.toJSONString("验证码正确");
        }else{
            toJson= JSON.toJSONString("验证码错误");
        }
       pw.println(toJson);
    }
    @RequestMapping(value = "/exit",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public void exit(HttpSession session){
        session.removeAttribute("manager");
        System.out.println(session.getAttribute("manager"));
        session.invalidate();
    }
}
