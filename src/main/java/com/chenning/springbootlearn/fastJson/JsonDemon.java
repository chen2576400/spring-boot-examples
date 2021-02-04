package com.chenning.springbootlearn.fastJson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenning.springbootlearn.demonBuild.model.User;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 15:52
 **/
public class JsonDemon {

    public void Test(String result){
        List<User> userList= JSON.parseArray(JSON.toJSONString(result),User.class) ;
        List<User> userList1 = JSON.parseObject(JSON.toJSONString(result),new TypeReference<List<User>>() {});
    }
}
