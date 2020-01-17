package com.yangzhichao.yzclib.aop.aspect;

import com.yangzhichao.yzclib.aop.annotation.SendCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2019/12/31
 * @class <code>redisAspect</code>
 * @see
 * @since JDK1.8
 */
@Aspect
@Component
public class redisAspect {
    Map<String, String> map;


    @Pointcut("@annotation(com.yangzhichao.yzclib.aop.annotation.SendCache)")
    public void whenNeedSeachCache() {

    }


    @Around("whenNeedSeachCache()")
    public Object around(ProceedingJoinPoint point) {
        map = new HashMap<>();
        map.put("dc:homePage", "data from redis");
        Object result = null;
        SendCache systemLog = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(SendCache.class);
        result = map.get(systemLog.key());
        if (Objects.isNull(result)) {
            try {
                result = point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;

    }


}