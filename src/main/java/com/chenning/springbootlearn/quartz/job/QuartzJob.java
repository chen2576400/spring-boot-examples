package com.chenning.springbootlearn.quartz.job;

import com.alibaba.fastjson.JSON;

import com.chenning.springbootlearn.quartz.config.SpringContextUtils;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;


public class QuartzJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.debug("--------------------定时任务开始执行--------------------");
        String json = context.getMergedJobDataMap().getString("JOB_PARAM_KEY");
        //将获取的对象序列化的json 转化为实体类对象
        ScheduleJob scheduleJob = JSON.parseObject(json, ScheduleJob.class);

        Integer jobId = scheduleJob.getJobId();
        String beanName = scheduleJob.getBeanName();
        String methodName = scheduleJob.getMethodName();
        String params = scheduleJob.getParams();

//            //quartz没有被spring管理 所以通过其它方式获取service
//            ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
//            //保存任务记录日志
//            ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
//            scheduleJobLog.setJobId(jobId);
//            scheduleJobLog.setBeanName(beanName);
//            scheduleJobLog.setMethodName(methodName);
//            scheduleJobLog.setParams(params);
//            scheduleJobLog.setCreateTime(new Timestamp(new Date().getTime()));

        long startTime = System.currentTimeMillis();

        try {
            Object targetClass = SpringContextUtils.getBean(beanName);   //获取对象
            Method method = null;
            //通过反射获取方法
            if (StringUtils.isNotBlank(params)) {
                method = targetClass.getClass().getDeclaredMethod(methodName, String.class);  /**    //得到某个类里面的某个方法
                 * getDeclaredMethod 可以获取任意方法 像，protected修饰的
                 * getMethod 只可以获取 修饰符为 public 的方法
                 */
            } else {
                method = targetClass.getClass().getDeclaredMethod(methodName);    //无参的
            }

            ReflectionUtils.makeAccessible(method);//使方法具有public权限
            //根据反射执行方法
            if (StringUtils.isNotBlank(params)) {
                method.invoke(targetClass, params);  //反射调用
            } else {
                method.invoke(targetClass);
            }

            long endTime = System.currentTimeMillis() - startTime;

//                scheduleJobLog.setStatus((byte) 0);//保存日志里的操作状态 0:成功
//                scheduleJobLog.setTimes(endTime);//耗时多长时间

            logger.info("任务执行成功，任务ID：" + jobId + "，总耗时：" + endTime + "毫秒");

        } catch (Exception e) {
            long endTime = System.currentTimeMillis() - startTime;
//                scheduleJobLog.setRemark(StringUtils.substring(e.toString(),2000));//错误消息
//                scheduleJobLog.setStatus((byte)1);//失败
//                scheduleJobLog.setTimes(endTime);//耗时

            e.printStackTrace();
            logger.error("任务执行失败，任务ID：" + jobId);
        } finally {
            //最后调用service保存定时任务日志记录
//                scheduleJobLogService.save(scheduleJobLog);
        }

    }


}


/**
 * @RequestMapping("zz") public String x(String a) {
 * return (a);
 * }
 * <p>
 * public text(){
 * Method method=null;
 * try {
 * //第一个参数是具体调用该方法的对象
 * //第二个参数是执行该方法的具体参数
 * method= this .getClass().getDeclaredMethod("x", String.class);    //这里的this代表当前controller（其他页面的可以用SpringContextUtils.getBean(beanName)获取对象）    x代表方法名
 * } catch (NoSuchMethodException e) {
 * e.printStackTrace();
 * }
 * try {
 * //第一个参数为类的实例(就是该method（方法）在哪个类里面)
 * //第二个参数为相应函数中的参数
 * method.invoke(this,"1111");                       //执行此方法
 * } catch (IllegalAccessException e) {
 * e.printStackTrace();
 * } catch (InvocationTargetException e) {
 * e.printStackTrace();
 * }
 * <p>
 * }
 */