package com.chenning.springbootlearn.lamda;

import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.demonBuild.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 15:08
 **/
public class LamdaDemon {
    @Autowired
    UserService service;

    public void example() {
        List<User> users = service.findAllUser();


        /**
         * 提取集合某个字段集合
         */
        {
            List<Long> staffIds = users.stream().map(sta -> {
                Integer s = sta.getUserId();
                return Long.valueOf(s);
            }).collect(Collectors.toList());
        }


        {
            List<String> usersNames = users.stream().map(User::getUserName).collect(Collectors.toList());
        }


        {
            List<Map<String, User>> listMaps = new ArrayList<>();//假设有值
            List<User> o = listMaps.stream().map(map -> map.get("key")).collect(Collectors.toList());
        }


        /**
         * list转map
         * 提取类的List的某个字段作为新Map的 key value
         *
         */
        {
            Map<Integer, String> staffMap = users.stream().collect(Collectors.toMap(x -> {
                return Integer.valueOf(x.getUserId());
            }, User::getUserName));
        }


        {
            Map<Integer, String> staffMap1 = users.stream().collect(Collectors.toMap(x -> {
                        return Integer.valueOf(x.getUserId());
                    }, User::getUserName, (oldValue, newValue) -> oldValue
            ));  //解决key值重复问题  如果key是重复的，你选择oldKey or newKey
        }


        /**
         * filter使用
         */
        {
            List<Integer> piGroups = users.stream().filter(user -> user.getAddress() != null).map(user -> {
                return user.getUserId();
            }).collect(Collectors.toList());
        }


        /**
         *
         * 字符串类型集合拼接符号
         */
        {
            List<String> stringList = null;
            final String joinSTR = String.join(",", stringList);
        }





        /**
         *
         * Java8根据对象集合某一列去重
         */
        {
            List<User> userList = users.stream().filter(distinctByKey(User::getUserName)).collect(Collectors.toList());

        }

    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
