package com.chenning.springbootlearn.handerJobDemon;

import com.chenning.springbootlearn.handerJobDemon.factory.HandlerFactory;
import com.chenning.springbootlearn.handerJobDemon.handler.BaseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 16:31
 **/
public class JobserviceImpl implements JobService {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    private BaseHandler  baseHandler;
    @Override
    public void statistic(Integer type) throws Exception {
        //将DemonMode设置好属性值
        DemonModel model=new DemonModel();
        model.setId(1);
        DemonContext context=new DemonContext(model);
        autowireCapableBeanFactory.autowireBean(context);
        context.init();

        Map<String, Object> params=new HashMap<>();
        params.put("parm","参数值");



        baseHandler= HandlerFactory.getStatisticalHandler(type);
        baseHandler.setDemonModel(model);
        autowireCapableBeanFactory.autowireBean(baseHandler);//这样每个handler里面注入对象可以拿到
        baseHandler.handler(params);

    }
}
