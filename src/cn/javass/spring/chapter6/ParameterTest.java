package cn.javass.spring.chapter6;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter6.service.IHelloWorldService;
import cn.javass.spring.chapter6.service.IPointcutService;

public class ParameterTest {
    
    @Test
    public void test() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/parameter.xml");
        IHelloWorldService helloService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        IPointcutService pointcutService = ctx.getBean("pointcutService", IPointcutService.class);
        //使用JoinPoint获取参数
        helloService.sayBefore("parameter");
        System.out.println("======================================");

        //自动获取
        pointcutService.test("parameter");
        System.out.println("======================================");
       
    }
    
    
}
