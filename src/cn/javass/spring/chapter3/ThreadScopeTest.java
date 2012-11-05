package cn.javass.spring.chapter3;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter2.helloworld.HelloApi;



public class ThreadScopeTest {
    
    @Test
    public void testSingleThread() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/threadScope.xml");
        HelloApi bean1 = beanFactory.getBean("helloApi", HelloApi.class);
        HelloApi bean2 = beanFactory.getBean("helloApi", HelloApi.class);
        //在同一线程中两次获取的Bean应该是相等的
        Assert.assertEquals(bean1, bean2);
    }

    @Test
    public void testTwoThread() throws InterruptedException {
        final BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/threadScope.xml");
        final HelloApi[] beans = new HelloApi[2];
        Thread thread1 = new Thread() {
            public void run() {
                beans[0] = beanFactory.getBean("helloApi", HelloApi.class);
            };
        };
        Thread thread2 = new Thread() {
            public void run() {
                beans[1] = beanFactory.getBean("helloApi", HelloApi.class);
            };
        };
        
        thread1.start(); thread1.sleep(1000);
        thread2.start(); thread2.sleep(1000);
        //在两个线程中两次获取的Bean应该是相等的
        Assert.assertNotSame(beans[0], beans[1]);
    }
    
}

