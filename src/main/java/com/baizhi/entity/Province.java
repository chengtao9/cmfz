package com.baizhi.entity;

import java.io.Serializable;

/**
 * Created by lala on 2018/6/3.
 */
public class Province implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Province() {
        super();
    }

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Province(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

