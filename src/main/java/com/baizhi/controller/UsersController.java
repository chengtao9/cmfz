package com.baizhi.controller;

import com.baizhi.entity.FieldUtils;
import com.baizhi.entity.Province;
import com.baizhi.entity.Users;
import com.baizhi.service.UsersService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by lala on 2018/6/3.
 */
@RestController
@RequestMapping("/user")
public class UsersController {
    @Autowired
    private UsersService usersService;

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }
    @RequestMapping(value = "/count",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Integer> queryDate(){
        Integer day1 = usersService.queryByDate(7);
        Integer day2 = usersService.queryByDate(14);
        Integer day3 = usersService.queryByDate(21);
        List<Integer> day=new ArrayList<Integer>();
        day.add(day1);
        day.add(day2-day1);
        day.add(day3-day2);
        return day;

    }
    @RequestMapping(value = "/show",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String,Object> queryAll(Integer page,Integer rows){
        Integer begin=(page-1)*rows;
        List<Users> users = usersService.queryAll(begin, rows);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("total",usersService.queryCount().size());
        map.put("rows",users);
        return map;
    }
    @RequestMapping(value = "/update",produces ={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void updateStatus(String status,String id){
        System.out.println(status+"   "+id);
        Users users = new Users();
        users.setId(id);
        users.setStatus(status);
        usersService.update(users);
    }
    @RequestMapping(value="/sex1",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Province> querySex1(){
        return usersService.queryBySex("男");
    }
    @RequestMapping(value="/sex2",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Province> querySex2(){
        return usersService.queryBySex("女");
    }
    @RequestMapping(value="export",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void export(HttpServletResponse response,String text,String fields){
        //得到属性中文名和英文名称的数组
        String[] texts=text.split(",");
        String[] field= fields.split(",");
        // 创建工具类对象
        Workbook workbook = new HSSFWorkbook();
        //设置日期格式
        DataFormat dataFormat = workbook.createDataFormat();
        short d = dataFormat.getFormat("yyyy年MM年dd日");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(d);
        //创建sheet
        Sheet sheet1 = workbook.createSheet("用户表");
        //创建第一行的表头
        Row row = sheet1.createRow(0);
        for (int i = 0; i < texts.length; i++) {
            String s = texts[i];
            //创建单元格对象，并给单元格对象赋值
            Cell cell = row.createCell(i);
            cell.setCellValue(s);
        }
        //创建数据行
        List<Users> users = usersService.queryCount();
        for (int i = 0; i < users.size(); i++) {
            Row dataRow = sheet1.createRow(i + 1);

            for (int j = 0; j < field.length; j++) {
                Cell cell = dataRow.createCell(j);
                String s1 = field[j];
                final Class<? extends Users> usersClass = users.get(i).getClass();
                String method="get"+s1.substring(0,1).toUpperCase()+s1.substring(1);
                try {
                    Object invoke = usersClass.getDeclaredMethod(method, null).invoke(users.get(i), null);
                    if(invoke instanceof Date){
                        sheet1.setColumnWidth(j,18*365);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue((Date)invoke);
                    }else{
                        cell.setCellValue(invoke.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String name = "持名法州用户表.xls";
        String fileName = "";
        try {
            fileName = new String(name.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("content-disposition", "attachment;fileName=" + fileName);
        response.setContentType("application/vnd.ms-excel");
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = "/field",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<FieldUtils> getField(){
        Class<Users> usersClass = Users.class;
        Field[] fields = usersClass.getDeclaredFields();
        List<FieldUtils>  list = new ArrayList<FieldUtils>();
        for (int i = 0; i < fields.length; i++) {
            Field field=fields[i];
            FieldUtils fieldUtils = new FieldUtils();
            fieldUtils.setId(field.getName());
            fieldUtils.setText(field.getAnnotation(FieldAnnotation.class).name());
            System.out.println(field.getName()+"  "+field.getAnnotation(FieldAnnotation.class).name());
           list.add(fieldUtils);
        }
        return list;

    }
    @RequestMapping("/import")
    public void importExcel(MultipartFile imageFile, HttpServletRequest request) throws Exception{
        String parent=null;
        String newName=null;
        if(imageFile.getOriginalFilename()!=null&&!imageFile.getOriginalFilename().equals("")){

            try {
                //拿到当前项目名
                String projectPath = request.getSession().getServletContext().getRealPath("/");
                //创建当前项目名对象
                File file = new File(projectPath);
                //拿到当前web路径
                 parent = file.getParent();
                File file1 = new File(parent + "/upload");
                if(!file1.exists()){
                    file1.mkdir();
                }
                //获得原始文件名
                String originalFilename = imageFile.getOriginalFilename();
                String s = UUID.randomUUID().toString();
                String extension = FilenameUtils.getExtension(originalFilename);
                newName=s+"."+extension;
                imageFile.transferTo(new File(file1.getPath(),newName));

            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        String path=parent+"\\upload\\"+newName;
        String replace = path.replace("\\", "/");
        List<Users> users = new ArrayList<Users>();
        Workbook workbook = new HSSFWorkbook(new FileInputStream(replace));
        Sheet sheet = workbook.getSheet("用户表");
        Class<Users> userClass = Users.class;
        Field[] field = userClass.getDeclaredFields();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Users user = new Users();
            Row row = sheet.getRow(i);

            System.out.println(i);
            for (int j=0;j<field.length;j++) {
                Field f=field[j];
                Cell cell = row.getCell(j);
                String methods = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                if (methods.equals("setId")) {
                    int numericCellValue = (int) cell.getNumericCellValue();
                    String s = String.valueOf(numericCellValue);

                    userClass.getDeclaredMethod(methods, String.class).invoke(user, s);
                } else if (methods.equals("setDate")) {
                    userClass.getDeclaredMethod(methods, Date.class).invoke(user, cell.getDateCellValue());
                } else {
                    userClass.getDeclaredMethod(methods, String.class).invoke(user, cell.getStringCellValue());
                }
            }
            System.out.println(user);
            users.add(user);
        }
     usersService.insert(users);
    }

}
