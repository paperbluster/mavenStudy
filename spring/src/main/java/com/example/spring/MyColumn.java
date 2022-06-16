package com.example.spring;

import java.lang.annotation.*;

/**将数据库字段转义成实体类对应字段对象
 * @author wanjun
 * @create 2022-06-16 23:57
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyColumn {
    String name() default ""; //数据库字段名
    MyEnum type();//转义规则
}
