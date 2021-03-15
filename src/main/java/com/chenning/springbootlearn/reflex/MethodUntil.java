package com.chenning.springbootlearn.reflex;

import java.lang.reflect.Method;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 17:08
 **/
public class MethodUntil {

    public static Method getMethod(Class<?> clazz, String name, Class<?>... paratmeterTypes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(name, paratmeterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method;
    }
}
