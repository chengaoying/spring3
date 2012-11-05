package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class OrderAspect1 {
        
    public void beforeAdvice1() {
        System.out.println("======OrderAspect1=====before advice1");
    }

    public void beforeAdvice2() {
        System.out.println("======OrderAspect1=====before advice2");
    }
    
    public void afterFinallyAdvice1() {
        System.out.println("======OrderAspect1=====after finally advice1");
    }

    public void afterFinallyAdvice2() {
        System.out.println("======OrderAspect1=====after finally advice2");
    }
    
    public void afterReturningAdvice1(Object retVal) {
        System.out.println("======OrderAspect1=====after returning advice1");
    }

    public void afterReturningAdvice2(Object retVal) {
        System.out.println("======OrderAspect1=====after returning advice2");
    }

    public void afterThrowingAdvice1(Exception exception) {
        System.out.println("======OrderAspect1=====after throwing advice1");
    }

    public void afterThrowingAdvice2(Exception exception) {
        System.out.println("======OrderAspect1=====after throwing advice2");
    }

    public Object aroundAdvice1(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("======OrderAspect1=====around before advice1");
        Object retVal = pjp.proceed();
        System.out.println("======OrderAspect1=====around after advice1");
        return retVal;
    }

    public Object aroundAdvice2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("======OrderAspect1=====around before advice2");
        Object retVal = pjp.proceed();
        System.out.println("======OrderAspect1=====around after advice2");
        return retVal;
    }
    
}
