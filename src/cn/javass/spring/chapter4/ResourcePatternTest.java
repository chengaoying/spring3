package cn.javass.spring.chapter4;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.spi.RealFileSystem;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourcePatternTest {
    
    @Test
    public void testClasspathPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        //只加载一个绝对匹配Resource，且通过ResourceLoader.getResource进行加载
        Resource[] resources = resolver.getResources("classpath:META-INF/INDEX.LIST");
        Assert.assertEquals(1, resources.length);

        //只加载一个匹配的Resource，且通过ResourceLoader.getResource进行加载
        resources = resolver.getResources("classpath:META-INF/*.LIST");
        Assert.assertTrue(resources.length == 1);
        

        //只加载一个绝对匹配Resource，且通过ResourceLoader.getResource进行加载
        resources = resolver.getResources("classpath:META-INF/MANIFEST.MF");
        Assert.assertEquals(1, resources.length);
    }
    

    @Test
    public void testClasspathAsteriskPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        //将加载多个绝对匹配的所有Resource
        //将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:META-INF/INDEX.LIST");
        Assert.assertTrue(resources.length > 1);
        
        //将加载多个模式匹配的Resource
        resources = resolver.getResources("classpath*:META-INF/*.LIST");
        Assert.assertTrue(resources.length > 1);

        
    }

    @Test
    public void testClasspathAsteriskPrefixLimit() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        
        //将首先通过ClassLoader.getResources("")加载目录，
        //将只返回文件系统的类路径不返回jar的跟路径
        //然后进行遍历模式匹配
        Resource[] resources = resolver.getResources("classpath*:asm-*.txt");
        Assert.assertTrue(resources.length == 0);

        //将通过ClassLoader.getResources("asm-license.txt")加载
        //asm-license.txt存在于com.springsource.net.sf.cglib-2.2.0.jar
        resources = resolver.getResources("classpath*:asm-license.txt");
        Assert.assertTrue(resources.length > 0);
        
        //将只加载文件系统类路径匹配的Resource
        resources = resolver.getResources("classpath*:LICENS*");
        Assert.assertTrue(resources.length == 1);
    }

    @Test
    public void testFilekPrefix() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:D:/*.txt");
        Assert.assertTrue(resources.length > 0);
    }

    @Test
    public void testVfsPrefix() throws IOException {
        //1.创建一个虚拟的文件目录
        VirtualFile home = VFS.getChild("/home");
        //2.将虚拟目录映射到物理的目录
        VFS.mount(home, new RealFileSystem(new File("d:")));
        //3.通过虚拟目录获取文件资源
        VirtualFile testFile = home.getChild("test.txt");
        
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("/home/test.txt");
        Assert.assertTrue(resources.length > 0);
        System.out.println(resources[0].getClass());
        
    }
    
}
