package cn.javass.spring.chapter12;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;

@javax.annotation.ManagedBean("managedBean")
public class TestManagedBean {
        
    @Resource
    private ApplicationContext ctx;
    
    public ApplicationContext getCtx() {
        return ctx;
    }
}
