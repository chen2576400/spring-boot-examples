package ext.st.pmgt.indicator.test;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Order
@Aspect
@Log4j2
public class LimitTimeAspect {




    @Getter
    private Map<String, List<Long>> limitMap = new HashMap<>();

    @Pointcut("@annotation(limitTime)")
    public void pointcut(LimitTime limitTime) {
    }

    @Around("pointcut(limitAccess)")
    public Object aroundLog(ProceedingJoinPoint point, LimitTime limitAccess) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            HttpServletRequest request = attributes.getRequest();
            String key = className + "." + methodName + "#" + request.getSession().getId();
            List<Long> millisecondList = limitMap.get(key);
            long now = System.currentTimeMillis();
            if (null == millisecondList) {
                List<Long> list = new ArrayList<>();
                list.add(now);
                limitMap.put(key, list);
            } else {
                List<Long> newMillisecondList = new ArrayList<>(millisecondList.size());
                millisecondList.forEach(millisecond -> {
                    // 当前访问时间 - 历史访问时间 < 限制时间
                    if (now - millisecond < limitAccess.timeout()) {
                        newMillisecondList.add(millisecond);
                    }
                });
                // 时间段内超过访问频次上限 - 阻断
                if (newMillisecondList.size() >= limitAccess.time()) {
                    log.info("接口调用过于频繁 {}", key);
                    StringBuffer buffer=new StringBuffer();
                    buffer.append(limitAccess.timeout()/1000);
                    buffer.append("s内只能允许访问");
                    buffer.append(limitAccess.time()+"次");
                    return Result.error(buffer.toString());
                }
                newMillisecondList.add(now);
                // 更新接口访问记录
                limitMap.put(key, newMillisecondList);
            }
        }
        return point.proceed();
    }
}



