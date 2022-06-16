package com.example.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author wanjun
 * @create 2022-06-16 23:39
 */
public class tool {

    public static Object mapTObject(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();//获取到所有属性，不包括继承的属性
            Method[] methods = clazz.getMethods();//获取本类中的方法，包含私有方法。
            methods = clazz.getDeclaredMethods();//获取本类中的方法，包含私有方法。
            Field[] supFields = obj.getClass().getSuperclass().getDeclaredFields();//获取传入类的父类的所有属性
//            for (Field field : fields) {
//                int mod = field.getModifiers();//获取字段的修饰符
//                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
//                    continue;
//                }
//                field.setAccessible(true);
////                boolean isIdField = field.isAnnotationPresent(MyColumn.class);
////                if(isIdField){
////                    field.set(obj, map.get(field.getAnnotation(MyColumn.class).));//根据属性名称去map获取value
////                }else{
////                    field.set(obj, map.get(field.getName()));//根据属性名称去map获取value
////                }
//                field.set(obj, map.get(field.getName()));//根据属性名称去map获取value
//
//            }
            for (Method method : methods) {//数据库字段映射到实体类主要通过反射SET方法或者是特殊需要转换的方法
                int mod = method.getModifiers();//获取字段的修饰符
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                method.setAccessible(true);
                boolean isIdField = method.isAnnotationPresent(MyColumn.class);
                if (isIdField) {
                    MyColumn myColumn = method.getAnnotation(MyColumn.class);//获取有注解标注的的方法的注解
                    String name = myColumn.name();//获取标注的字段名称
                    method.invoke(obj, map.get(name));//执行有注解标注的方法
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}
