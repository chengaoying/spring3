package cn.javass.spring.chapter12;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

public class TestBean41 {
    @Resource(name = "message")
    private String message;
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    @PostConstruct
    public void init() {
        System.out.println("==========init");
    }
    
    @PreDestroy
    public void destroy() {
        System.out.println("==========destroy");
        
    }
}
