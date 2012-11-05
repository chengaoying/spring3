package cn.javass.spring.chapter12.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.javass.spring.chapter12.service.impl.TestServiceImpl;

@Controller
public class TestAction {
    
    @Autowired
    private TestServiceImpl testService;
    
    public void list() {
        //调用业务逻辑层方法
    }
}
