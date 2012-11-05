package cn.javass.spring.chapter2;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter2.helloworld.HelloApi;


public class NamingBeanTest {
    
    @Test
    public void test1() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean1.xml");
        //根据类型获取bean
        HelloApi helloApi = beanFactory.getBean(HelloApi.class);
        helloApi.sayHello();
    }

    @Test
    public void test2() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean2.xml");
        //根据id获取bean
        HelloApi bean = beanFactory.getBean("bean", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test3() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean3.xml");
        //根据name获取bean
        HelloApi bean = beanFactory.getBean("bean", HelloApi.class);
        bean.sayHello();
    }

    @Test
    public void test4() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean4.xml");
        //根据id获取bean
        HelloApi bean1 = beanFactory.getBean("bean1", HelloApi.class);
        bean1.sayHello();
        //根据别名获取bean
        HelloApi bean2 = beanFactory.getBean("alias1", HelloApi.class);
        bean2.sayHello();
        //根据id获取bean
        HelloApi bean3 = beanFactory.getBean("bean3", HelloApi.class);
        bean3.sayHello();
        
        String[] bean3Alias = beanFactory.getAliases("bean3");
        //因此别名不能和id一样，如果一样则由IoC容器负责消除冲突
        Assert.assertEquals(0, bean3Alias.length);

    }
    
    @Test
    public void test5() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean5.xml");
        //根据id获取bean
        HelloApi bean1 = beanFactory.getBean("bean1", HelloApi.class);
        bean1.sayHello();
        //2根据别名获取bean
        HelloApi alias11 = beanFactory.getBean("alias11", HelloApi.class);
        alias11.sayHello();
        //验证确实是四个别名        
        String[] bean1Alias = beanFactory.getAliases("bean1");
        System.out.println("=======namingbean5.xml bean1 别名========");
        for(String alias : bean1Alias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean5.xml bean1 别名========");
        Assert.assertEquals(4, bean1Alias.length);

        //根据id获取bean
        HelloApi bean2 = beanFactory.getBean("bean2", HelloApi.class);
        bean2.sayHello();
        ////2根据别名获取bean
        HelloApi alias21 = beanFactory.getBean("alias21", HelloApi.class);
        alias21.sayHello();
        //验证确实是两个别名
        String[] bean2Alias = beanFactory.getAliases("bean2");
        System.out.println("=======namingbean5.xml bean2 别名========");
        for(String alias : bean2Alias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean5.xml bean2 别名========");
        Assert.assertEquals(2, bean2Alias.length);
        
    }
    
    @Test
    public void test6() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter2/namingbean6.xml");
        //根据id获取bean
        HelloApi bean = beanFactory.getBean("bean", HelloApi.class);
        bean.sayHello();

        //根据别名获取bean
        HelloApi alias1 = beanFactory.getBean("alias1", HelloApi.class);
        alias1.sayHello();
        HelloApi alias2 = beanFactory.getBean("alias2", HelloApi.class);
        alias2.sayHello();
        
        String[] beanAlias = beanFactory.getAliases("bean");
        System.out.println("=======namingbean6.xml bean 别名========");
        for(String alias : beanAlias) {
            System.out.println(alias);
        }
        System.out.println("=======namingbean6.xml bean 别名========");
        Assert.assertEquals(2, beanAlias.length);

    }
    
}
