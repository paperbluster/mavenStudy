import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wanjun
 * @create 2022-05-16 22:55
 */
public class invokeTest {


    public void shout(String input){
        System.out.println("hello,"+input);
    }

    @Test
    public void vertify() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 一般执行一个类的方法：new 这个类再去调用该对象的方法
        invokeTest a=new invokeTest();
        a.shout("jojo");


        // 使用反射Method代替A这个类去执行A的方法
        //获取字节码对象,这里要填好你对应对象的包的路径
        Class<invokeTest> clazz = (Class<invokeTest>) Class.forName("invokeTest");
        //形式一：获取一个对象
        Constructor<invokeTest> con =  clazz.getConstructor();
        invokeTest m = con.newInstance();
        //获取Method对象,即要代理执行的方法
        Method method = clazz.getMethod("shout", String.class);
        method.invoke(m,"dio");
    }

}
