package com.chenning.springbootlearn.fastJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 15:52
 **/
public class JsonDemon {
    /**
     * 踩坑知识点1：
     * 转换实体类时   json字段和实体类字段不一致  使用build注解时  要补齐无参构造方法和有参构造方法
     * @param result
     */

    public void Test(String result){
        List<User> userList= JSON.parseArray(JSON.toJSONString(result),User.class) ;
        List<User> userList1 = JSON.parseObject(JSON.toJSONString(result),new TypeReference<List<User>>() {});


        User user = JSON.parseObject("json", User.class);
    }
}
