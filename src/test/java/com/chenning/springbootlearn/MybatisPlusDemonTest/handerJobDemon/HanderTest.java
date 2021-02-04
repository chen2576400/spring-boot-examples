package com.chenning.springbootlearn.MybatisPlusDemonTest.handerJobDemon;

import com.chenning.springbootlearn.SpringBootLearnApplication;
import com.chenning.springbootlearn.handerJobDemon.JobService;
import com.chenning.springbootlearn.handerJobDemon.JobserviceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 09:56
 **/
@SpringBootTest(classes = SpringBootLearnApplication.class)
@RunWith(SpringRunner.class)
public class HanderTest {
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Test
    public  void test() throws Exception {
        JobService jobService=new JobserviceImpl();
        autowireCapableBeanFactory.autowireBean(jobService);
        jobService.statistic("1001");
    }
}
