package com.chenning.springbootlearn.MybatisPlusDemonTest.qrtz;

import com.chenning.springbootlearn.SpringBootLearnApplication;
import com.chenning.springbootlearn.quartz.config.SpringContextUtils;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;
import com.chenning.springbootlearn.quartz.service.QuartzService;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 15:06
 **/
@SpringBootTest(classes = SpringBootLearnApplication.class)
@RunWith(SpringRunner.class)
@Log
public class QuartzJobTest {
    @Autowired(required = false)
    private QuartzService quartzService;

    @Test
    public void save() {  //添加
        ScheduleJob scheduleJob = ScheduleJob.builder().beanName("jobserviceImpl").methodName("statistic")
                .params("1001").cronExpression("*/5 * * * * ?").build();
        String beanName = scheduleJob.getBeanName();
        String methodName = scheduleJob.getMethodName();
        String cron = scheduleJob.getCronExpression();
        String Parm = scheduleJob.getParams();
        if (null == SpringContextUtils.getBean(beanName)) {
            log.info("BeanName名称不正确，或没有注册到Spring容器中");
        }
        if (!CronExpression.isValidExpression(cron)) {
            log.info("cron表达式错误，请重新设置！");
        }
        quartzService.insertJob(scheduleJob);

    }


    /**
     * 添加可以在test方法执行 其余的要去controller  test方法执行一次就结束了
     */

    @Test
    public void run() {    //启动
        quartzService.runJob(1);
    }

    @Test
    public void delete() { //删除
        quartzService.deleteJob(1);
    }

    @Test
    public void resumejob() {  //恢复
        quartzService.resumeJob(1);
    }

    @Test
    public void pauseJob() {  //恢复
        quartzService.pausejob(1);
    }

}
