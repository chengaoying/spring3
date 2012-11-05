package cn.javass.spring.chapter4;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.jboss.vfs.TempFileProvider;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.jboss.vfs.spi.RealFileSystem;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.VfsResource;

public class ResourceTest {
    
    
    @Test
    public void testByteArrayResource() {
        Resource resource = new ByteArrayResource("Hello World!".getBytes());
        if(resource.exists()) {
            dumpStream(resource);
        }
        Assert.assertEquals(false, resource.isOpen());
    }
    
    @Test
    public void testInputStreamResource() {
        ByteArrayInputStream bis = new ByteArrayInputStream("Hello World!".getBytes());
        Resource resource = new InputStreamResource(bis);
        if(resource.exists()) {
            dumpStream(resource);
        }
        Assert.assertEquals(true, resource.isOpen());
    }
    
    @Test
    public void testFileResource() {
        File file = new File("d:/test.txt");
        Resource resource = new FileSystemResource(file);
        if(resource.exists()) {
            dumpStream(resource);
        }
        if(resource.exists()) {
            dumpStream(resource);
        }
        Assert.assertEquals(false, resource.isOpen());
    }
    
    @Test
    public void testClasspathResourceByDefaultClassLoader() throws IOException {
        Resource resource = new ClassPathResource("cn/javass/spring/chapter4/test1.properties");
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
    }

    @Test
    public void testClasspathResourceByClassLoader() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        Resource resource = new ClassPathResource("cn/javass/spring/chapter4/test1.properties" , cl);
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
    }
    
    @Test
    public void testClasspathResourceByClass() throws IOException {
        Class clazz = this.getClass();
        Resource resource1 = new ClassPathResource("cn/javass/spring/chapter4/test1.properties" , clazz);
        if(resource1.exists()) {
            dumpStream(resource1);
        }
        System.out.println("path:" + resource1.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource1.isOpen());
        
        Resource resource2 = new ClassPathResource("test1.properties" , this.getClass());
        if(resource2.exists()) {
            dumpStream(resource2);
        }
        System.out.println("path:" + resource2.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource2.isOpen());

    }
    
    @Test
    public void testClasspathResourceFromJar() throws IOException {
        Resource resource = new ClassPathResource("overview.html");
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getURL().getPath());
        Assert.assertEquals(false, resource.isOpen());

    }

    
    @Test
    public void testUrlResource() throws IOException {
        Resource resource = new UrlResource("file:d:/test.txt");
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getURL().getPath());
        Assert.assertEquals(false, resource.isOpen());

        Resource resource2 = new UrlResource("http://www.springsource.org");
        if(resource2.exists()) {
            dumpStream(resource2);
        }
        System.out.println("path:" + resource2.getURL().getPath());
        Assert.assertEquals(false, resource2.isOpen());

    }

    
    @Test
    public void testVfsResource1() throws IOException {
        VirtualFile virtualFile = VFS.getChild("d:/test.txt");
        Resource resource = new VfsResource(virtualFile);
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getURL().getPath());
        Assert.assertEquals(false, resource.isOpen());
        
    }

    @Test
    public void testVfsResourceForRealFileSystem() throws IOException {
        //1.创建一个虚拟的文件目录
        VirtualFile home = VFS.getChild("/home");
        //2.将虚拟目录映射到物理的目录
        VFS.mount(home, new RealFileSystem(new File("d:")));
        //3.通过虚拟目录获取文件资源
        VirtualFile testFile = home.getChild("test.txt");
        //4.通过一致的接口访问
        Resource resource = new VfsResource(testFile);
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
        
    }

    @Test
    public void testVfsResourceForJar() throws IOException {
        //1.首先获取jar包路径
        File realFile = new File("lib/org.springframework.beans-3.0.5.RELEASE.jar");
        //2.创建一个虚拟的文件目录
        VirtualFile home = VFS.getChild("/home2");
        //3.将虚拟目录映射到物理的目录
        VFS.mountZipExpanded(realFile, home, TempFileProvider.create("tmp", Executors.newScheduledThreadPool(1)));
        //4.通过虚拟目录获取文件资源
        VirtualFile testFile = home.getChild("META-INF/spring.handlers");
        //5.通过一致的接口访问
        Resource resource = new VfsResource(testFile);
        if(resource.exists()) {
            dumpStream(resource);
        }
        System.out.println("path:" + resource.getFile().getAbsolutePath());
        Assert.assertEquals(false, resource.isOpen());
        
    }



    private void dumpStream(Resource resource) {
        InputStream is = null;
        try {
            //1.获取文件资源
            is = resource.getInputStream();
            //2.读取资源
            byte[] descBytes = new byte[is.available()]; 
            is.read(descBytes);
            System.out.println(new String(descBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                //3.关闭资源
                is.close();
            } catch (IOException e) {
            }
        }
    }
    
}
