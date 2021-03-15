package com.chenning.springbootlearn.handerJobDemon.factory;


import com.chenning.springbootlearn.handerJobDemon.handler.BaseHandler;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 16:26
 **/
public class HandlerFactory {
    public  static BaseHandler getStatisticalHandler(Integer type) throws Exception{
        Class<BaseHandler> clazz= (Class<BaseHandler>) Class.forName("com.chenning.springbootlearn.handerJobDemon.handler.Handler"+type);
        BaseHandler newInstance = clazz.newInstance(); //就相当于new对象
        return newInstance;
    }
}
