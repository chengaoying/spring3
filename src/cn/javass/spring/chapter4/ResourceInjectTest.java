package cn.javass.spring.chapter4;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import cn.javass.spring.chapter4.bean.ResourceBean3;
import cn.javass.spring.chapter4.bean.ResourceBean4;

public class ResourceInjectTest {
    
    @Test
    public void test() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter4/resourceInject.xml");
        ResourceBean3 resourceBean1 = ctx.getBean("resourceBean1", ResourceBean3.class);
        ResourceBean3 resourceBean2 = ctx.getBean("resourceBean2", ResourceBean3.class);
        Assert.assertTrue(resourceBean1.getResource() instanceof ClassPathResource);
        Assert.assertTrue(resourceBean2.getResource() instanceof ClassPathResource);
    }

    @Test
    public void testResourceArrayInject() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter4/resourceArrayInject.xml");
        ResourceBean4 resourceBean1 = ctx.getBean("resourceBean1", ResourceBean4.class);
        ResourceBean4 resourceBean2 = ctx.getBean("resourceBean2", ResourceBean4.class);
        ResourceBean4 resourceBean3 = ctx.getBean("resourceBean3", ResourceBean4.class);
        
        Assert.assertEquals(2, resourceBean1.getResources().length);
        Assert.assertTrue(resourceBean2.getResources().length > 1);
        Assert.assertTrue(resourceBean3.getResources().length > 1);
        
    }
    
    
}
