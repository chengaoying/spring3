package cn.javass.spring.chapter12.configuration;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter2.helloworld.HelloApi;

public class ConfigurationTest {
    
    
    @Test
    public void testHelloworld() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        Assert.assertEquals("hello", ctx.getBean("message"));
        Assert.assertNotNull(ctx.getBean("ctxConfig"));
    }
    

    @Test
    public void  testDependencyInject() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        ctx.getBean("helloImpl3", HelloApi.class).sayHello();
        ctx.getBean("helloImpl4", HelloApi.class).sayHello();
    }

    @Test
    public void  testLookupMethodInject() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        System.out.println("=======prototype sayHello======");
        HelloApi helloApi2 = ctx.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();
        helloApi2 = ctx.getBean("helloApi2", HelloApi.class);
        helloApi2.sayHello();

    }
    @Test
    public void  testImport() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext(ApplicationContextConfig2.class);
        Assert.assertEquals("hello", ctx.getBean("message"));
    }

    @Test
    public void  testImportResource() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        Assert.assertEquals("test", ctx.getBean("message3"));
    }

    public void  testXmlConfig() {
        String configLocations[] = {"chapter12/configuration/xml-config.xml"};
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext(configLocations);
        Assert.assertEquals("hello", ctx.getBean("message"));
    }

    public void  testMultipleConfig() {
        AnnotationConfigApplicationContext ctx1 = 
            new AnnotationConfigApplicationContext(
                    ApplicationContextConfig.class,
                    ApplicationContextConfig2.class);

        AnnotationConfigApplicationContext ctx2 = 
            new AnnotationConfigApplicationContext();
        ctx2.register(ApplicationContextConfig.class);
        ctx2.register(ApplicationContextConfig2.class);
    }

    public void  testComponentScan() {
        AnnotationConfigApplicationContext ctx = 
            new AnnotationConfigApplicationContext();
        ctx.scan("cn.javass.chapter12.confiuration");
        ctx.refresh();
        Assert.assertEquals("hello", ctx.getBean("message"));
        
    }
}
