package com.chenning.springbootlearn.quartz.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("qrtz_schedule_job")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleJob implements Serializable {
    private static final long serialVersionUID = 1L;
    //标记数据表主键
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;

    private String beanName; //执行的类名

    private String methodName; //方法名

    private String params; //参数

    private String cronExpression; //cron表达式

    private Byte status; //任务状态 0，运行 1，暂停

    private String remark; //备注

    private Timestamp createTime; //创建时间

    private Timestamp updateTime; //修改时间
}