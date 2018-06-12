package com.baizhi.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lala on 2018/5/31.
 */
public class Chapter implements Serializable {
    private  String id;
    private String title;
    private String size;
    private String duration;
    private  String downPath;
    private Date uploadDate;
    private String oldName;
    private String pid;

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Chapter(String id, String title, String size, String duration, String downPath, Date uploadDate, String oldName, String pid) {
        this.id = id;
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.downPath = downPath;
        this.uploadDate = uploadDate;
        this.oldName = oldName;
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", size='" + size + '\'' +
                ", duration='" + duration + '\'' +
                ", downPath='" + downPath + '\'' +
                ", uploadDate=" + uploadDate +
                ", oldName='" + oldName + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }

    public Chapter() {
        super();
    }
}
