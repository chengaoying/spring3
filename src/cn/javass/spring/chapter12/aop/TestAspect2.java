package cn.javass.spring.chapter12.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
@Aspect
public class TestAspect2 {
    
    @Pointcut(value="execution(* *(..))")
    private void pointcut() {}

    @After(value="pointcut()")
    public void before() {
        System.out.println("=======after");
    }
}
