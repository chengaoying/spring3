package cn.javass.spring.chapter3;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter3.bean.DependentBean;



public class MoreDependencyInjectTest {
    
    @Test
    public void testDependOn() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chapter3/depends-on.xml");
        context.registerShutdownHook();
        DependentBean dependentBean = context.getBean("dependentBean", DependentBean.class);
        dependentBean.write("aaa");
    }
    
   
}

