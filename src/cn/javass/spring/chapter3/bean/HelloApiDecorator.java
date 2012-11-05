package cn.javass.spring.chapter3.bean;

import java.util.List;

import cn.javass.spring.chapter2.helloworld.HelloApi;

public class HelloApiDecorator implements HelloApi {
    
    private HelloApi helloApi;
    
    private String message;
    
    public HelloApiDecorator() {
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public HelloApiDecorator(HelloApi helloApi) {
        this.helloApi = helloApi;
    }
    
    public void setHelloApi(HelloApi helloApi) {
        this.helloApi = helloApi;
    }
    
    @Override
    public void sayHello() {
        System.out.println("==========装饰开始===========");
        helloApi.sayHello();
        System.out.println("==========装饰完毕===========");
    }
    
}
