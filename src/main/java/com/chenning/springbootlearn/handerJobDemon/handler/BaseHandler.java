package com.chenning.springbootlearn.handerJobDemon.handler;

import com.chenning.springbootlearn.handerJobDemon.DemonModel;
import lombok.Data;

import java.util.Map;


@Data
public abstract class BaseHandler {
    private DemonModel demonModel;

    public abstract void handler(Map<String, Object> params);

}
