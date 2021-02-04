package com.chenning.springbootlearn.quartz.service;

import com.chenning.springbootlearn.quartz.model.ScheduleJob;

public interface QuartzService {

    void insertJob(ScheduleJob scheduleJob);  //添加

    void pausejob(Integer jobId);//暂停

    void deleteJob(Integer jobId); //删除

    void resumeJob(Integer jobId); //恢复

    void runJob(Integer jobId); //启动
}
