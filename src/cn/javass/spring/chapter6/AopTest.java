package cn.javass.spring.chapter6;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter6.service.IHelloWorldService;
import cn.javass.spring.chapter6.service.IIntroductionService;
import cn.javass.spring.chapter6.service.IPayService;

public class AopTest {
    
    @Test
    public void testHelloworld() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/helloworld.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayHello();
        Assert.assertTrue(AopUtils.isAopProxy(helloworldService));
    }
    
    @Test
    public void testSchemaBeforeAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayBefore("before");
        System.out.println("======================================");
    }

    @Test
    public void testSchemaAfterReturningAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterReturning();
        System.out.println("======================================");
    }

    @Test(expected = RuntimeException.class)
    public void testSchemaAfterThrowingAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterThrowing();
        System.out.println("======================================");
    }
    @Test(expected = RuntimeException.class)
    public void testSchemaAfterFinallyAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterFinally();
        System.out.println("======================================");
    }

    @Test
    public void testSchemaAroundAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAround("haha");
        System.out.println("======================================");
    }

    @Test
    public void testSchemaIntroduction() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IIntroductionService introductionService = ctx.getBean("helloWorldService", IIntroductionService.class);
        introductionService.induct();
        System.out.println("======================================");
    }

    @Test
    public void testSchemaAdvisor() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAdvisorBefore("haha");
        System.out.println("======================================");
    }
    
    @Test
    public void seperator() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    
    @Test
    public void testAnnotationBeforeAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayBefore("before");
        System.out.println("======================================");
    }
    
    @Test
    public void testAnnotationAfterReturningAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterReturning();
        System.out.println("======================================");
    }
    
    @Test(expected = RuntimeException.class)
    public void testAnnotationAfterThrowingAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterThrowing();
        System.out.println("======================================");
    }
    @Test(expected = RuntimeException.class)
    public void testAnnotationAfterFinallyAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAfterFinally();
        System.out.println("======================================");
    }
    
    @Test
    public void testAnnotationAroundAdvice() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IHelloWorldService helloworldService = ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayAround("haha");
        System.out.println("======================================");
    }
    
    @Test
    public void testAnnotationIntroduction() {
        System.out.println("======================================");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/advice2.xml");
        IIntroductionService introductionService = ctx.getBean("helloWorldService", IIntroductionService.class);
        introductionService.induct();
        System.out.println("======================================");
    }
    
    

    @Test
    public void testSchema() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter6/schemeAop.xml");
        IPayService payService = ctx.getBean("pointPayService", IPayService.class);
        payService.pay(1, 1);
    }
}
