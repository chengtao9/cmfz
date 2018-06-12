package com.baizhi.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lala on 2018/5/29.
 */
public class Menu implements Serializable {
    private String id;
    private String name;
    private String iconcls;
    private  String url;
    private String pid;
    private List<Menu> children;

    public Menu(String id, String name, String iconcls, String url, String pid) {
        this.id = id;
        this.name = name;
        this.iconcls = iconcls;
        this.url = url;
        this.pid = pid;
    }

    public Menu() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Menu(String id, String name, String iconcls, String url, String pid, List<Menu> children) {

        this.id = id;
        this.name = name;
        this.iconcls = iconcls;
        this.url = url;
        this.pid = pid;
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", iconcls='" + iconcls + '\'' +
                ", url='" + url + '\'' +
                ", pid='" + pid + '\'' +
                ", children=" + children +
                '}';
    }
}
