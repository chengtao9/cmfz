package com.baizhi.service;

import com.baizhi.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lala on 2018/5/29.
 */
public interface MenuService {
    List<Menu> queryAll();
}
