package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.ProceedingJoinPoint;


public class PayServiceAspect {
       
    public void before() {
        System.out.println("==========before advice");
    }
    
    public void afterReturning(boolean retVal) {
        System.out.println("==========after returning advice");
    }
    
    public void afterThrowing(Exception exception) {
        System.out.println("==========after throwing advice");
    }
    
    public void afterFinally() {
        System.out.println("==========after finally advice");
    }
    
    public Object around(ProceedingJoinPoint call) throws Throwable {
        System.out.println("==========around(before) advice");
        Object result = call.proceed();
        System.out.println("==========around(after) advice");
        return result;
    }
    
    
}
