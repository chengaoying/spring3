package cn.javass.spring.chapter3;

import org.junit.Test;

import cn.javass.spring.chapter2.HelloImpl2;



public class BeanFatoryTest {
    
    @Test
    public void testPrototype() throws Exception {
        //1.创建Bean工厂
        DefaultBeanFactory bf = new DefaultBeanFactory();
        //2.创建原型 Bean定义
        BeanDefinition bd = new BeanDefinition();
        bd.setId("bean");
        bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        bd.setClazz(HelloImpl2.class.getName());
        bf.registerBeanDefinition(bd);
        //对于原型Bean每次应该返回一个全新的Bean
        System.out.println(bf.getBean("bean") != bf.getBean("bean"));
    }

    @Test
    public void testSingleton() throws Exception {
        //1.创建Bean工厂
        DefaultBeanFactory bf = new DefaultBeanFactory();
        //2.创建单例 Bean定义
        BeanDefinition bd = new BeanDefinition();
        bd.setId("bean");
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);
        bd.setClazz(HelloImpl2.class.getName());
        bf.registerBeanDefinition(bd);
        //对于单例Bean应该返回同一个Bean
        System.out.println(bf.getBean("bean") == bf.getBean("bean"));
    }
}

