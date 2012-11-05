package cn.javass.spring.chapter3;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.javass.spring.chapter2.helloworld.HelloApi;
import cn.javass.spring.chapter3.bean.ArrayTestBean;
import cn.javass.spring.chapter3.bean.BooleanTestBean;
import cn.javass.spring.chapter3.bean.CollectionTestBean;
import cn.javass.spring.chapter3.bean.IdRefTestBean;
import cn.javass.spring.chapter3.bean.ListTestBean;
import cn.javass.spring.chapter3.bean.MapTestBean;
import cn.javass.spring.chapter3.bean.NavigationA;
import cn.javass.spring.chapter3.bean.NavigationC;
import cn.javass.spring.chapter3.bean.PropertiesTestBean;
import cn.javass.spring.chapter3.bean.SetTestBean;


public class DependencyInjectTest {
    
    @Test
    public void testConstructorDependencyInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/constructorDependencyInject.xml");
        
        //获取根据参数索引依赖注入的Bean
        HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
        byIndex.sayHello();

        //获取根据参数类型依赖注入的Bean
        HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
        byType.sayHello();

        //获取根据参数名字依赖注入的Bean
        HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
        byName.sayHello();
        
    }

    @Test
    public void testStaticFactoryDependencyInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/staticFactoryDependencyInject.xml");
        
        //获取根据参数索引依赖注入的Bean
        HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
        byIndex.sayHello();
        
        //获取根据参数类型依赖注入的Bean
        HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
        byType.sayHello();
        
        //获取根据参数名字依赖注入的Bean
        HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
        byName.sayHello();
        
    }

    @Test
    public void testInstanceFactoryDependencyInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/instanceFactoryDependencyInject.xml");
        
        //获取根据参数索引依赖注入的Bean
        HelloApi byIndex = beanFactory.getBean("byIndex", HelloApi.class);
        byIndex.sayHello();
        
        //获取根据参数类型依赖注入的Bean
        HelloApi byType = beanFactory.getBean("byType", HelloApi.class);
        byType.sayHello();
        
        //获取根据参数名字依赖注入的Bean
        HelloApi byName = beanFactory.getBean("byName", HelloApi.class);
        byName.sayHello();
        
    }
    
    @Test
    public void testSetterDependencyInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/setterDependencyInject.xml");
        
        HelloApi bean = beanFactory.getBean("bean", HelloApi.class);
        bean.sayHello();
    }
//=====================依赖注入扩展部分=================================
    
    //注入Boolean值
    @Test
    public void testBooleanInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/booleanInject.xml");
        
        BooleanTestBean bean1 = beanFactory.getBean("bean1", BooleanTestBean.class);
        System.out.println(bean1.isSuccess());

        BooleanTestBean bean2 = beanFactory.getBean("bean2", BooleanTestBean.class);
        System.out.println(bean2.isSuccess());

        BooleanTestBean bean3 = beanFactory.getBean("bean3", BooleanTestBean.class);
        System.out.println(bean3.isSuccess());

        BooleanTestBean bean4 = beanFactory.getBean("bean4", BooleanTestBean.class);
        System.out.println(bean4.isSuccess());
    }

    //注入Bean ID
    @Test
    public void testIdRefInject() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/idRefInject.xml");

        IdRefTestBean bean1 = beanFactory.getBean("idrefBean1", IdRefTestBean.class);
        System.out.println(bean1.getId());
        
        IdRefTestBean bean2 = beanFactory.getBean("idrefBean2", IdRefTestBean.class);
        System.out.println(bean2.getId());
    }

    //注入java.util.List值
    @Test
    public void testListInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/listInject.xml");
        ListTestBean listBean = beanFactory.getBean("listBean", ListTestBean.class);
        System.out.println(listBean.getValues().size());
        Assert.assertEquals(3, listBean.getValues().size());
        
    }

    //注入java.util.Set值
    @Test
    public void testSetInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/setInject.xml");
        SetTestBean setBean = beanFactory.getBean("setBean", SetTestBean.class);
        System.out.println(setBean.getValues().size());
        Assert.assertEquals(3, setBean.getValues().size());
        
    }
    

    //注入java.util.Collection值
    @Test
    public void testCollectionInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/collectionInject.xml");
        
        //本质是List类型
        CollectionTestBean collectionBean1 = beanFactory.getBean("collectionBean1", CollectionTestBean.class);
        System.out.println("Type:" + collectionBean1.getValues().getClass().getName());
        System.out.println(collectionBean1.getValues().size());
        Assert.assertEquals(3, collectionBean1.getValues().size());

        //本质是Set类型
        CollectionTestBean collectionBean2 = beanFactory.getBean("collectionBean2", CollectionTestBean.class);
        System.out.println("Type:" + collectionBean2.getValues().getClass().getName());
        System.out.println(collectionBean2.getValues().size());
        Assert.assertEquals(3, collectionBean2.getValues().size());
        
    }

    //注入数组类型值
    @Test
    public void testArrayInject() {
        
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/arrayInject.xml");
        ArrayTestBean arrayBean = beanFactory.getBean("arrayBean", ArrayTestBean.class);
        System.out.println(arrayBean.getArray().length);
        System.out.println(arrayBean.getArray2().length);
        System.out.println(arrayBean.getArray2()[0].length);
        System.out.println(arrayBean.getArray2()[1].length);
        
    }

    //注入Map类型值
    @Test
    public void testMapInject() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/mapInject.xml");
        MapTestBean mapBean = beanFactory.getBean("mapBean", MapTestBean.class);
        System.out.println(mapBean.getValues().size());
    }

    
    //注入Properties类型值
    @Test
    public void testPropertiesInject() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/propertiesInject.xml");
        PropertiesTestBean propertiesBean = beanFactory.getBean("propertiesBean", PropertiesTestBean.class);
        System.out.println(propertiesBean.getValues().size());
        System.out.println(propertiesBean.getValues().containsValue("22"));

        PropertiesTestBean propertiesBean2 = beanFactory.getBean("propertiesBean2", PropertiesTestBean.class);
        System.out.println(propertiesBean2.getValues().size());
        System.out.println(propertiesBean2.getValues().containsKey("1"));
        System.out.println(propertiesBean2.getValues().containsKey("2"));
        System.out.println(propertiesBean2.getValues().containsKey("3"));
        System.out.println(propertiesBean2.getValues().containsKey("4"));
        System.out.println(propertiesBean2.getValues().containsKey("5"));
        System.out.println(propertiesBean2.getValues().containsValue("11"));
    }
    
    //注入其他Bean类型值 通过ref bean
    @Test
    public void testBeanInject() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("chapter3/beanInject.xml");
        //通过构造器方式注入
        HelloApi bean1 = beanFactory.getBean("bean1", HelloApi.class);
        bean1.sayHello();
        //通过setter方式注入
        HelloApi bean2 = beanFactory.getBean("bean2", HelloApi.class);
        bean2.sayHello();
    }


    //注入其他Bean类型值 通过ref local/parent
    @Test
    public void testLocalAndparentBeanInject() {
        //初始化父容器
        ApplicationContext parentBeanContext = new ClassPathXmlApplicationContext("chapter3/parentBeanInject.xml");
        //初始化当前容器
        ApplicationContext beanContext = new ClassPathXmlApplicationContext(new String[] {"chapter3/localBeanInject.xml"}, parentBeanContext);
        //该Bean引用local bean
        HelloApi bean1 = beanContext.getBean("bean1", HelloApi.class);
        bean1.sayHello();
        //该Bean引用parent bean
        HelloApi bean2 = beanContext.getBean("bean2", HelloApi.class);
        bean2.sayHello();
    }

    //注入其他Bean类型值 通过inner bean
    @Test
    public void testInnerBeanInject() {
        ApplicationContext context = new ClassPathXmlApplicationContext("chapter3/innerBeanInject.xml");

        HelloApi bean = context.getBean("bean", HelloApi.class);
        bean.sayHello();
        
    }

    //对象图导航
    @Test
    public void testNavigationBeanInject() {
        ApplicationContext context = new ClassPathXmlApplicationContext("chapter3/navigationBeanInject.xml");
        
        //获取导航Bean
        NavigationA navigationA = context.getBean("a", NavigationA.class);
        //获取通过导航注入方式注入的NavigationC
        navigationA.getNavigationB().getNavigationC().sayNavigation();
        //获取通过导航注入方式注入的list条目
        navigationA.getNavigationB().getList().get(0).sayNavigation();
        //获取通过导航注入方式注入的map条目
        navigationA.getNavigationB().getMap().get("key").sayNavigation();
        //获取通过导航注入方式注入的数组条目
        navigationA.getNavigationB().getArray()[0].sayNavigation();
        //获取通过导航注入方式注入的Properties条目
        ((NavigationC)navigationA.getNavigationB().getProperties().get("1")).sayNavigation();
        
        
    }

    @Test
    public void testPNamespaceBeanInject() {
        ApplicationContext context = new ClassPathXmlApplicationContext("chapter3/pNamespaceInject.xml");
        Assert.assertEquals("value", context.getBean("idrefBean1", IdRefTestBean.class).getId());
        Assert.assertEquals("test", context.getBean("idrefBean2", IdRefTestBean.class).getId());
        
    }
}
