package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * Created by lala on 2018/5/31.
 */
@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    public ChapterService getChapterService() {
        return chapterService;
    }

    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public AlbumService getAlbumService() {
        return albumService;
    }

    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }
    @RequestMapping(value="/queryAll",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public  Map<String,Object> queryAll(Integer page,Integer rows){
        Integer begin=(page-1)*rows;
        List<Album> titlePics = albumService.queryPage(begin, rows);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",albumService.queryCount());
        map.put("rows",titlePics);
        return map;
    }
    @RequestMapping(value="/insert",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void upload(MultipartFile imageFile, Album album, HttpServletRequest request){
        if(imageFile.getOriginalFilename()!=null&&!imageFile.getOriginalFilename().equals("")) try {
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
            String originalFilename = imageFile.getOriginalFilename();
            String s = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(originalFilename);
            String newName = s + "." + extension;
            imageFile.transferTo(new File(file1.getPath(), newName));

            album.setCoverImg("/upload/" + newName);
            album.setId(UUID.randomUUID().toString().replace("-", ""));
            albumService.inert(album);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping(value="/chapter",produces ={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void insertChapter(MultipartFile image,String id,HttpServletRequest request){
        System.out.println(image);
        if(image.getOriginalFilename()!=null&&!image.getOriginalFilename().equals("")) try {
            //拿到当前项目名
            String projectPath = request.getSession().getServletContext().getRealPath("/");
            //创建当前项目名对象
            File file = new File(projectPath);
            //拿到当前web路径
            String parent = file.getParent();
            File file1 = new File(parent + "/redio");
            if (!file1.exists()) {
                file1.mkdir();
            }
            //获得原始文件名
            String originalFilename = image.getOriginalFilename();
            String s = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(originalFilename);
            String newName = s + "." + extension;
            image.transferTo(new File(file1.getPath(), newName));
            String upload=parent+"\\redio\\"+newName;
            String replace = upload.replace("\\", "/");
            File fl = new File(replace);

            MP3AudioHeader audioHeader=null;
            String str=null;
            try {
                MP3File f = (MP3File) AudioFileIO.read(fl);
                audioHeader = (MP3AudioHeader)f.getAudioHeader();
                int trackLength = audioHeader.getTrackLength();
                int minute=trackLength/60;
                int second=trackLength%60;
                str=minute+"分"+second+"秒";
            } catch(Exception e) {
                e.printStackTrace();
            }

            Chapter chapter = new Chapter();
            chapter.setDownPath("\\redio\\"+newName);
            chapter.setId(UUID.randomUUID().toString().replace("-", ""));
            chapter.setOldName(originalFilename);
            chapter.setPid(id);
            DecimalFormat df=new DecimalFormat("0.00");
            String format = df.format((double) image.getSize()/(1024*1024))+"M";
            chapter.setSize(format);
            chapter.setTitle(originalFilename);
            System.out.println(str);
            chapter.setDuration(str);
            chapter.setUploadDate(new Date());
            chapterService.inert(chapter);

            Album album = albumService.queryById(id);
            album.setCount(album.getCount()+1);
            albumService.update(album);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping(value="/down")
    public void download(String downPath, String oldName, HttpServletRequest request, HttpServletResponse response){
        System.out.println(downPath+" "+oldName);
        //获取当前项目位置
        String projectPath = request.getSession().getServletContext().getRealPath("/");
        File file = new File(projectPath);
        String parent = file.getParent();
        String realPath=parent+downPath;
        File downFile = new File(realPath);
        //设置相应头
        try {
            String fileName=new String(oldName.getBytes("UTF-8"),"ISO8859-1");
            response.setHeader("content-disposition", "attachment;fileName=" + fileName);
            response.setContentType("audio/mpeg");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(downFile));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
