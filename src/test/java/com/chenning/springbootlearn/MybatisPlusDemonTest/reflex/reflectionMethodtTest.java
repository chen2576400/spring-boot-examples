package com.chenning.springbootlearn.MybatisPlusDemonTest.reflex;

import com.chenning.springbootlearn.SpringBootLearnApplication;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.quartz.config.SpringContextUtils;
import com.chenning.springbootlearn.reflex.model.service.BaseService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 16:40
 **/
@SpringBootTest(classes = SpringBootLearnApplication.class)
@RunWith(SpringRunner.class)
public class reflectionMethodtTest {


    @SneakyThrows
    @Test
    public void test() {
        User user = new User();
        user.setUserId(1);
        user.setUserName("哮天犬");
        Method method = null;

        Class<?> aClass = Class.forName("com.chenning.springbootlearn.reflex.model.service.BaseService");
//            aClass = SpringContextUtils.getBean(BaseService.class).getClass();//这种方式前提该Demon1类要交给spring管理
//            aClass=Demon1.class;

        /* getDeclaredMethod 可以获取任意方法 像，protected修饰的getMethod 只可以获取 修饰符为 public 的方法*/
        method = aClass.getDeclaredMethod("x", User.class);
//          method = aClass.getClass().getDeclaredMethod("x", new Class<?>[]{User.class});


        //invoke方法第一个要放类的实例，如果没有类的实例就要写成  method.invoke(BaseService.class.newInstance(), user);
        //这个类的实例可以从容器取 也可以new对象
        //由于我这里BaseService交给了容器管理，所以通过工厂获取该对象可拿到里面的注入对象
        method.invoke(SpringContextUtils.getBean(BaseService.class), user);

    }






}
