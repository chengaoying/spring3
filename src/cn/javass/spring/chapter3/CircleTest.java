package cn.javass.spring.chapter3;

import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class CircleTest {
    
    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testcircleByConstructor() throws Throwable {
        try {
            new ClassPathXmlApplicationContext("chapter3/circleInjectByConstructor.xml");
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
    }
    @Test
    public void testCircleBySetterAndSingleton1() throws Throwable {
        new ClassPathXmlApplicationContext("chapter3/circleInjectBySetterAndSingleton.xml");
    }

    @Test(expected = BeanCurrentlyInCreationException.class)
    public void testCircleBySetterAndSingleton2() throws Throwable {
        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
            ctx.setConfigLocation("chapter3/circleInjectBySetterAndSingleton.xml");
            ctx.refresh();
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
       
    }
    
    @Test(expected = BeanCurrentlyInCreationException.class)
    public void circleBySetterAndPrototypeTest() throws Throwable {
        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("chapter3/circleInjectBySetterAndPrototype.xml");
            System.out.println(ctx.getBean("circleA"));
        }
        catch (Exception e) {
            //因为要在创建circle3时抛出；
            Throwable e1 = e.getCause().getCause().getCause();
            throw e1;
        }
    }
    
}

