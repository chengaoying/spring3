package cn.javass.spring.chapter6;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter6.service.IPointcutService;

public class InstanceModelTest {
    
    
    @Test
    public void test() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/instanceModel.xml");
        IPointcutService testService1 = ctx.getBean("pointcutService1", IPointcutService.class);

        IPointcutService testService2 = ctx.getBean("pointcutService2", IPointcutService.class);

        testService1.test();
        System.out.println("======================================");
        testService2.test();
        System.out.println("======================================");
    }
    
    
}
