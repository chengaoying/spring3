package cn.javass.spring.chapter6.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdviceImpl implements MethodBeforeAdvice {
    
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("===========before advice");
    }
    
}
