package com.baizhi.test;

import com.aliyuncs.exceptions.ClientException;
import com.baizhi.util.MessageUtil;
import org.junit.Test;

/**
 * Created by lala on 2018/6/6.
 */
public class TestMessage {
    @Test
    public void m1(){
        try {
            MessageUtil.sendMessage("17600048029","88888");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
