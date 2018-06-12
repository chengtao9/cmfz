package com.baizhi.entity;

/**
 * Created by lala on 2018/6/4.
 */
public class FieldUtils {
    private  String  text;
    private String  id;

    public FieldUtils(String text, String id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FieldUtils{" +
                "text='" + text + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public FieldUtils() {
        super();
    }
}
