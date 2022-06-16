package com.example.spring;

import java.lang.annotation.*;

/**
 * @author wanjun
 * @create 2022-06-16 23:57
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyColumn {
    String name() default ""; //数据库字段名
    MyEnum type();
}
