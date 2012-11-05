package cn.javass.spring.chapter2.helloworld;

public class HelloImpl implements HelloApi {

    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
    
    public void destroy() {
        System.out.println("de");
    }
}
