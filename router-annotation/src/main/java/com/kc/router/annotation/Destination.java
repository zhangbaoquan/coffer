package com.kc.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RetentionPolicy.CLASS 说明当前注解可以被保留的时间
 * @author zhangbaoquan
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Destination {
    /**
     * 当前页面的URL，不能为空
     *
     * @return 页面URL
     */
    String url();

    /**
     * 对于当前页面的中文描述
     *
     * @return 例如 "个人主页"
     */
    String description();
}
