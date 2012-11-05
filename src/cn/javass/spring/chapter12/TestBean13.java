package cn.javass.spring.chapter12;

import org.springframework.beans.factory.annotation.Autowired;

public class TestBean13 {
    
    private String message;

    @Autowired
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
