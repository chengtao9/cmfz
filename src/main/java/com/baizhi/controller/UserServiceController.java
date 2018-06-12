package com.baizhi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import com.baizhi.util.MessageUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by lala on 2018/6/6.
 */
@RestController
@RequestMapping("/service")
public class UserServiceController {
    @Autowired
    private TitlePicService titlePicService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private UsersService usersService;
    @RequestMapping(value = "/all",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject show(String uid,String type,String id){
        JSONObject jsonObject = new JSONObject();
        if(type!=null){
            if(type.equals("all")){
                List<TitlePic> titlePics = titlePicService.queryStatus("n");
                jsonObject.put("header",titlePics);
                List<Album> albums = albumService.queryShow();
                jsonObject.put("album",albums);
                List<Article> articles = articleService.queryByNewDate();
                jsonObject.put("artical",articles);
                return jsonObject;
            }
        }
        jsonObject.put("errMessage","参数格式不正确,请求失败");
        return jsonObject;
    }
    @RequestMapping(value = "/wen",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject getWen(String id,String type){
        JSONObject jsonObject = new JSONObject();
        if(type!=null){
            if(type.equals("wen")){
                Album album = albumService.queryById(id);
                List<Chapter> chapters = chapterService.queryByPid(id);
                jsonObject.put("introduction",album);
                jsonObject.put("list",chapters);
                return jsonObject;
            }
        }
        jsonObject.put("errMessage","参数格式不正确，请求失败");
        return jsonObject;
    }
    @RequestMapping(value = "/login",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject login(String phone,String password){
        JSONObject jsonObject = new JSONObject();
        Users users = usersService.queryByUsername(phone);
        if(users==null){
            jsonObject.put("error","-200");
            jsonObject.put("errmsg","账户不存在");
            return jsonObject;
        }else {

            if(users.getPassword().equals(password)){
                jsonObject.put("user",users);
                return jsonObject;
            }
            jsonObject.put("error","-200");
            jsonObject.put("errormsg","密码错误");
            return jsonObject;
        }
    }
    @RequestMapping(value = "register",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject register(String phone,String password){
        JSONObject jsonObject = new JSONObject();
        Users users = usersService.queryByUsername(phone);
        if(users!=null){
            jsonObject.put("error","-200");
            jsonObject.put("error_msg","账户存在");
            return jsonObject;
        }else{

            Users users1 = new Users();
            users1.setId(UUID.randomUUID().toString().replace("-",""));
            users1.setStatus("n");
            users1.setDate(new Date());
            users1.setPassword(password);
            users1.setPhonenum(phone);
            int i = usersService.insertSelective(users1);
            System.out.println(i);
            jsonObject.put("user1",users1);
            return jsonObject;
        }
    }
    @RequestMapping(value = "/update",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject updateUser(String uid, String gender, MultipartFile photo, String location ,
                                 String description , String nickname , String province , String city ,
                                 String password, HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        if(photo.getOriginalFilename()!=null&&!photo.getOriginalFilename().equals("")) {
            try {
                //拿到当前项目名
                String projectPath = request.getSession().getServletContext().getRealPath("/");
                //创建当前项目名对象
                File file = new File(projectPath);
                //拿到当前web路径
                String parent = file.getParent();
                File file1 = new File(parent + "/upload");
                if (!file1.exists()) {
                    file1.mkdir();
                }
                //获得原始文件名
                String originalFilename = photo.getOriginalFilename();
                String s = UUID.randomUUID().toString();
                String extension = FilenameUtils.getExtension(originalFilename);
                String newName = s + "." + extension;
                photo.transferTo(new File(file1.getPath(), newName));
                Users users = usersService.selectByPrimaryKey(uid);
                users.setPassword(password);
                users.setHeadpic("/upload/" + newName);
                users.setSex(gender);
                users.setCity(city);
                users.setLocation(location);
                users.setSign(description);
                users.setUsername(nickname);
                users.setProvince(province);
                usersService.updateByPrimaryKeySelective(users);
                jsonObject.put("user",users);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            jsonObject.put("error_msg","头像上传失败");
            jsonObject.put("error","-200");
        }
        return jsonObject;
    }
    @RequestMapping(value = "/message",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void sendMessage(final String phone,final HttpSession session){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(9);
            stringBuffer.append(i1);
        }
        String code=stringBuffer.toString();
        session.setAttribute(phone,code);
        //设置验证码有效时长5分钟
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute(phone);

            }

        },5*60*1000);

        try {
            MessageUtil.sendMessage(phone,code);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/checkCode",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject checkCode(String phone,String code,HttpSession session){
        JSONObject jsonObject = new JSONObject();
        if(session.getAttribute(phone)==null){
            jsonObject.put("result","验证超时");

        }else{
            if(session.getAttribute(phone).equals(code)){
                jsonObject.put("result","success");
            }else {
                jsonObject.put("result","验证码输入错误");
            }
        }
        return jsonObject;
    }
    @RequestMapping(value = "/vip",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public JSONObject getVIP(String uid){
        JSONObject jsonObject = new JSONObject();
        List<Users> users = usersService.queryVIP(uid);
        jsonObject.put("users",users);
        return jsonObject;

    }
}
