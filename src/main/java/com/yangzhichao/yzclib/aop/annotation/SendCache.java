package com.yangzhichao.yzclib.aop.annotation;

import javax.validation.constraints.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2019/12/31
 * @class <code>SendCache</code>
 * @see
 * @since JDK1.8
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SendCache {
    @NotNull
    String key();
}