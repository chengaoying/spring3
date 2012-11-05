package cn.javass.spring.chapter4;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

public class ResourceLoaderTest {
    
    @Test
    public void testResourceLoad() {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:cn/javass/spring/chapter4/test1.txt");
        //验证返回的是ClassPathResource
        Assert.assertEquals(ClassPathResource.class, resource.getClass());
        
        Resource resource2 = loader.getResource("file:cn/javass/spring/chapter4/test1.txt");
        //验证返回的是ClassPathResource
        Assert.assertEquals(UrlResource.class, resource2.getClass());
        
        Resource resource3 = loader.getResource("cn/javass/spring/chapter4/test1.txt");
        //验证返默认可以加载ClasspathResource
        Assert.assertTrue(resource3 instanceof ClassPathResource);
        
    }
}
