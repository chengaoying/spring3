package cn.javass.spring.chapter3;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter2.helloworld.HelloApi;
import cn.javass.spring.chapter3.bean.ListBean;



public class AutowireBeanTest {
    
    @Test
    public void testAutowireByName() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byName.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType1() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byType1.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType2_1() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byType2-1.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByType2_2() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byType2-2.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }
    @Test
    public void testAutowireByType2_3() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byType2-2.xml");
        ListBean listBean = context.getBean("listBean", ListBean.class);
        //对于集合接口根据类型注入将自动查找所有匹配的注入
        Assert.assertTrue(listBean.getList1().size() > 0);
        //对于集合具体类型将根据具体类型进行注入，而不是选择所有泛型类型信息匹配的Bean
        Assert.assertTrue(listBean.getList2() == null);
    }

    @Test
    public void testAutowireByConstructor() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-byConstructor.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void testAutowireByAutoDetect() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/autowire-autodetect.xml");
        HelloApi helloApi = context.getBean("bean", HelloApi.class);
        helloApi.sayHello();
    }
    
   
}

