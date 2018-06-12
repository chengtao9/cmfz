package com.baizhi.controller;

import com.baizhi.entity.TitlePic;
import com.baizhi.service.TitlePicService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lala on 2018/5/29.
 */
@Controller
@RequestMapping("/titlePic")
public class TitlePicController {
    @Autowired
    private TitlePicService titlePicService;

    public TitlePicService getTitlePicService() {
        return titlePicService;
    }

    public void setTitlePicService(TitlePicService titlePicService) {
        this.titlePicService = titlePicService;
    }
    @ResponseBody
    @RequestMapping(value="/queryAll",produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Integer begin=(page-1)*rows;
        List<TitlePic> titlePics = titlePicService.queryAll(begin, rows);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",titlePicService.size());
        map.put("rows",titlePics);
        return map;
    }
    @ResponseBody
    @RequestMapping(value="/insert",produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void update(MultipartFile imageFile, TitlePic titlePic, HttpServletRequest request){
        System.out.println(titlePic);
        if(imageFile.getOriginalFilename()!=null&&!imageFile.getOriginalFilename().equals("")){

            try {
                //拿到当前项目名
                String projectPath = request.getSession().getServletContext().getRealPath("/");
                //创建当前项目名对象
                File file = new File(projectPath);
                //拿到当前web路径
                String parent = file.getParent();
                File file1 = new File(parent + "/upload");
                if(!file1.exists()){
                    file1.mkdir();
                }
                //获得原始文件名
                String originalFilename = imageFile.getOriginalFilename();
                String s = UUID.randomUUID().toString();
                String extension = FilenameUtils.getExtension(originalFilename);
                String newName=s+"."+extension;
                imageFile.transferTo(new File(file1.getPath(),newName));

                titlePic.setImgPath("/upload/"+newName);
                if(titlePic.getStatus()==null){
                    titlePic.setStatus("n");
                }else{
                    titlePic.setStatus("y");
                }
                titlePic.setId(UUID.randomUUID().toString().replace("-",""));
                titlePicService.insert(titlePic);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    @RequestMapping(value="/update",produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public void updateStatus(String status,String id){
        titlePicService.updataStatus(status,id);
    }
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(TitlePic titlePic){
        System.out.println(titlePic.getId());
        titlePicService.delete(titlePic.getId());
    }

}
