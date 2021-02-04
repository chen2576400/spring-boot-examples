package com.chenning.springbootlearn.quartz.until;

import com.alibaba.fastjson.JSON;
import com.chenning.springbootlearn.quartz.job.QuartzJob;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class JobUtil {
    /**
     * 创建任务
     */
    public static void addJob(Scheduler scheduler, ScheduleJob scheduleJob) {

        try {
            Integer jobId = scheduleJob.getJobId();
            //创建Job对象
            JobDetail job = JobBuilder.newJob(QuartzJob.class).withIdentity("JOB_" + jobId).build();
            //获取cron表达式 并创建对象
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            //创建触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("TRIGGER_" + jobId)
                    .withSchedule(cronScheduleBuilder) //将cron表达式配置到触发器
                    .build();

            //将对象josn序列化存储到Job的getJobDataMap()方法中，为后续根据获取属性执行对应的类的任务
            job.getJobDataMap().put("JOB_PARAM_KEY", JSON.toJSONString(scheduleJob));
            //存数据
               scheduler.scheduleJob(job, trigger);
//            if(Constant.ScheduleStatus.PAUSE.getValue()==scheduleJob.getStatus()) {
//                scheduler.pauseJob(JobKey.jobKey("JOB_" + jobId));//使任务处于等待状态,创建后不会执行
//            }else{
//                scheduler.resumeJob(JobKey.jobKey("JOB_" + jobId));
//            }
        } catch (SchedulerException e) {
             e.printStackTrace();
        }
    }


    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void pauseJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.pauseJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }





    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.resumeJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }



    /**
     * 删除任务
     */
    public static void deleteJob(Scheduler scheduler, Integer jobId) throws SchedulerException {
        try {
            scheduler.deleteJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();

        }
    }

    /**
     * 立即执行定时任务
     */
    public static void run(Scheduler scheduler, Integer jobId) {
        try {
            //只执行一次并且不会改变任务的状态
            scheduler.triggerJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();

        }
    }





}