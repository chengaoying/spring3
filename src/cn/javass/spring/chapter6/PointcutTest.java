package cn.javass.spring.chapter6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter6.service.IHelloWorldService;
import cn.javass.spring.chapter6.service.IPointcutService;
import cn.javass.spring.chapter6.service.IPointcutService.TestMap;

public class PointcutTest {
    
    
    @Test
    public void test() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/pointcut.xml");
        IPointcutService testService = ctx.getBean("pointcutService", IPointcutService.class);
        IHelloWorldService helloService = ctx.getBean("helloService", IHelloWorldService.class);
        
        System.out.println("*********************************");
        testService.test();
        System.out.println("*********************************");
        testService.test("");
        
        System.out.println("*********************************");
        helloService.sayHello();
        System.out.println("*********************************");
        
        System.out.println("*********************************");
        testService.test(new Date());
        System.out.println("*********************************");
        
        System.out.println("*********************************");
        testService.test(new Model());
        System.out.println("*********************************");


        System.out.println("*********************************");
        testService.test("", "");
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new Model(), new Model());
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new HashMap());
        testService.test(new HashMap(), "");
        testService.test(new HashMap(), 1);
        System.out.println("*********************************");
        
        System.out.println("*********************************");
        testService.test(new ArrayList());
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new HashSet<TestMap>());
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new Stack<Map>());
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new Serializable(){});
        System.out.println("*********************************");

        System.out.println("*********************************");
        testService.test(new Model());
        System.out.println("*********************************");

        System.out.println("*********************************");
        //用于测试@args 注意将掉用 public boolean test(Object obj);
        testService.test((Object)new Model());
        System.out.println("*********************************");

        System.out.println("======================================");
    }
    
    
}
