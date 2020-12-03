package ext.st.pmgt.indicator.test;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitTime {
    int time() default 1; // 访问次数，默认为10次

    long timeout() default 1000; // 过期时间，时间戳间隔

}