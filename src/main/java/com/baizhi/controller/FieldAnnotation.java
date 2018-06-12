package com.baizhi.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lala on 2018/6/4.
 */

@Target(ElementType.FIELD)
//注解使用时机
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {
    public String name();
}
