package cn.javass.spring.chapter12;

import org.springframework.beans.factory.annotation.Autowired;

public class TestBean11 {
    
    private String message;
    
    @Autowired(required=false)
    private TestBean11(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
