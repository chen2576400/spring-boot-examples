package com.chenning.springbootlearn.quartz.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.chenning.springbootlearn.quartz.model.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface JobMapper extends BaseMapper<ScheduleJob> {

}
