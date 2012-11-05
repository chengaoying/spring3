package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class OrderAspect2 {
    
    @Pointcut(value="execution(* *(..))")
    public void pointcut() {}
    
    @Before(value="pointcut()")
    public void beforeAdvice1() {
        System.out.println("======OrderAspect2=====before advice1");
    }

    @Before(value="pointcut()")
    public void beforeAdvice2() {
        System.out.println("======OrderAspect2=====before advice2");
    }
    
    @Around(value="pointcut()")
    public Object aroundAdvice1(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("======OrderAspect2=====around before advice1");
        Object retVal = pjp.proceed();
        System.out.println("======OrderAspect2=====around after advice1");
        return retVal;
    }

    @Around(value="pointcut()")
    public Object aroundAdvice2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("======OrderAspect2=====around before advice2");
        Object retVal = pjp.proceed();
        System.out.println("======OrderAspect2=====around after advice2");
        return retVal;
    }
    
    @AfterReturning(value="pointcut()", argNames="retVal", returning="retVal")
    public void afterReturningAdvice1(Object retVal) {
        System.out.println("======OrderAspect2=====after returning advice1");
    }

    @AfterReturning(value="pointcut()", argNames="retVal", returning="retVal")
    public void afterReturningAdvice2(Object retVal) {
        System.out.println("======OrderAspect2=====after returning advice2");
    }
    
    @AfterThrowing(value="pointcut()", argNames="retVal", throwing="retVal")
    public void afterThrowingAdvice1(Exception exception) {
        System.out.println("======OrderAspect2=====after throwing advice1");
    }

    @AfterThrowing(value="pointcut()", argNames="retVal", throwing="retVal")
    public void afterThrowingAdvice2(Exception exception) {
        System.out.println("======OrderAspect2=====after throwing advice2");
    }
    
    @After(value="pointcut()")
    public void afterFinallyAdvice1() {
        System.out.println("======OrderAspect2=====after finally advice1");
    }

    @After(value="pointcut()")
    public void afterFinallyAdvice2() {
        System.out.println("======OrderAspect2=====after finally advice2");
    }
    
    
}
