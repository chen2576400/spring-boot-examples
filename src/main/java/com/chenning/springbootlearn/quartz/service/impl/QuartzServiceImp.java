package com.chenning.springbootlearn.quartz.service.impl;


import com.chenning.springbootlearn.quartz.mapper.JobMapper;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;
import com.chenning.springbootlearn.quartz.service.QuartzService;
import com.chenning.springbootlearn.quartz.until.JobUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class QuartzServiceImp implements QuartzService {
    @Autowired
    private JobMapper jobMapper;

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    @Override  //添加任务
    public void insertJob(ScheduleJob scheduleJob) {
        try {
            scheduleJob.setCreateTime(new Timestamp(System.currentTimeMillis()));
            jobMapper.insert(scheduleJob);
            JobUtil.addJob(scheduler, scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override    //暂停任务
    public void pausejob(Integer jobId) {
        JobUtil.pauseJob(scheduler, jobId);
        ScheduleJob scheduleJob = jobMapper.selectById(jobId);
        scheduleJob.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//        scheduleJob.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
        jobMapper.updateById(scheduleJob);
    }


    @Override         //恢复任务
    public void resumeJob(Integer jobId) {
        JobUtil.resumeJob(scheduler, jobId);
        ScheduleJob scheduleJob = jobMapper.selectById(jobId);
        scheduleJob.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        jobMapper.updateById(scheduleJob);

    }


    @Override  //删除任务
    public void deleteJob(Integer jobId) {
        try {
            JobUtil.deleteJob(scheduler, jobId);
            jobMapper.deleteById(jobId);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override       //启动任务
    public void runJob(Integer jobId) {
        JobUtil.run(scheduler, jobId);
    }
}
