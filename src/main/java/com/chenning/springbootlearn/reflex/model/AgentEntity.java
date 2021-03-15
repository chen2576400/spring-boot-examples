package com.chenning.springbootlearn.reflex.model;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 17:11
 **/
@Data
@Builder
public class AgentEntity {
    private Object object;  //类名
    private Method method; //方法名
    private Object[] args; //参数名
}
