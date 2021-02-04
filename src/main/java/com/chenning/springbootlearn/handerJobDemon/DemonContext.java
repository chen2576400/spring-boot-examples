package com.chenning.springbootlearn.handerJobDemon;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 16:43
 **/
@Data
public class DemonContext {
    @Getter
    private  DemonModel demonModel;
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    private DemonFactory demonFactory;
    public DemonContext(DemonModel demonModel ) {
        this.demonModel = demonModel;
        this.demonFactory = new DemonFactory(demonModel);
    }


    public void init(){
        autowireCapableBeanFactory.autowireBean(demonFactory);
        //数据通道入口要放在第一个 这样其他几个才能够根据这个job获取值来查找
        demonFactory.getUserInfoGetter().initUserInfo(demonModel);

        //后面数据是通过第一个入口user对象 然后以此为条件查询到其他对象信息
        demonFactory.getCardInfoGetter().initCardInfo(demonModel);
    }
}
