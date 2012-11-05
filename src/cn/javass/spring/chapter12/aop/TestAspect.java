package cn.javass.spring.chapter12.aop;

import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class TestAspect {
    
    @Pointcut(value="execution(* *(..))")
    private void pointcut() {}

    @Before(value="pointcut()")
    public void before() {
        System.out.println("=======before");
    }
}
