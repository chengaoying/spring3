package cn.javass.spring.chapter3;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

import cn.javass.spring.chapter2.HelloImpl2;



public class SingletonTest {
    
    @Test
    public void testSingleton() throws Exception {
        //第一个单例
        //1.创建一个ClassLoader
        ClassLoader classLoader = new SingletonClassLoader();
        //2.加载需要的类
        Class clazz = classLoader.loadClass("cn.javass.spring.chapter3.bean.Singleton");
        //3.通过反射获取单例对象
        Method getInstance = clazz.getDeclaredMethod("getInstance");
        Object singletonObj = getInstance.invoke(clazz);
        //4.通过反射获取字段counter值
        Field counterField = clazz.getDeclaredField("counter");
        counterField.setAccessible(true);
        Integer counter = (Integer) counterField.get(singletonObj);
        //5.对字段counter自增1
        counterField.set(singletonObj, counter + 1);
        //6.验证counter=1
        Assert.assertEquals(1, counterField.get(singletonObj));
        System.out.println(counterField.get(singletonObj));
        
        //第二个单例
        //1.创建一个ClassLoader
        ClassLoader classLoader2 = new SingletonClassLoader();
        //2.加载需要的类
        Class clazz2 = classLoader2.loadClass("cn.javass.spring.chapter3.bean.Singleton");
        //3.通过反射获取单例对象
        Method getInstance2 = clazz2.getDeclaredMethod("getInstance");
        Object singletonObj2 = getInstance2.invoke(clazz2);
        //4.通过反射获取字段counter值
        Field counterField2 = clazz2.getDeclaredField("counter");
        //5.对字段counter自增1
        counterField2.setAccessible(true);
        Integer counter2 = (Integer) counterField2.get(singletonObj2);
        counterField2.set(singletonObj2, counter2 + 1);
        //6.验证counter=1
        Assert.assertEquals(1, counterField2.get(singletonObj2));
        System.out.println(counterField2.get(singletonObj2));
        
        
        //以上就证明了每个ClassLoader一个单例
        
    }
    
    @Test
    public void testRegister() {
        SingletonBeanRegister register = new SingletonBeanRegister();
        register.registerSingleton("bean1", new HelloImpl2());
        HelloImpl2 bean1 = (HelloImpl2) register.getSingleton("bean1");
        bean1.sayHello();
        
        try {
            register.registerSingleton("bean1", new HelloImpl2());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}

