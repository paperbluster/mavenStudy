package com.example.spring;

/**
 * @author wanjun
 * @create 2022-06-16 23:59
 */
public enum MyEnum {

    Object2Integer(Integer.class, StringToBaseType.class),
    Object2Float(Float.class, StringToBaseType.class),
    Object2Long(Long.class, StringToBaseType.class),
    Object2Byte(Byte.class, StringToBaseType.class),
    Object2Short(Short.class, StringToBaseType.class),
    Object2String(String.class, StringToBaseType.class),
    Object2Double(Double.class, StringToBaseType.class),
    Object2IntIntMap(Integer.class,Integer.class,baseMapAdapter.class);


//    private String codeName;
//    private String adapterName;
    private Class<?> type;
    private Class<StringToBaseType> abstractType;

    private Class<baseMapAdapter> baseMapType;
    private Class<?> key;
    private Class<?> value;

    MyEnum(Class<?> type, Class<StringToBaseType> abstractType) {
        this.type=type;
        this.abstractType= abstractType;
    }

    MyEnum(Class<?> key,Class<?> value, Class<baseMapAdapter> abstractType) {
        this.key=key;
        this.value=value;
        this.baseMapType= abstractType;
    }
}
