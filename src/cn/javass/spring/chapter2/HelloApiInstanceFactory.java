package cn.javass.spring.chapter2;

import cn.javass.spring.chapter2.helloworld.HelloApi;


public class HelloApiInstanceFactory {
    
    public HelloApi newInstance(String message) {
        return new HelloImpl2(message);
    }
    
}
