package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;

import cn.javass.spring.chapter6.service.IIntroductionService;

@Aspect
public class HelloWorldAspect2 {
        
    @Pointcut(value="execution(* cn.javass..*.sayBefore(java.lang.String)) && args(param)", argNames = "param")
    public void beforePointcut(String param) {}
    
    @Before(value = "beforePointcut(param)", argNames = "param")
    public void beforeAdvice(String param) {
        System.out.println("===========before advice param:" + param);
    }
        
    @AfterReturning(
            value="execution(* cn.javass..*.sayBefore(..))",
            pointcut="execution(* cn.javass..*.sayAfterReturning(..))",
            argNames="retVal", returning="retVal")
    public void afterReturningAdvice(Object retVal) {
        System.out.println("===========after returning advice retVal:" + retVal);
    }
    
    @AfterThrowing(
            value="execution(* cn.javass..*.sayAfterThrowing(..))",
            argNames="exception", throwing="exception")
    public void afterThrowingAdvice(Exception exception) {
        System.out.println("===========after throwing advice exception:" + exception);
    }

    @After(value="execution(* cn.javass..*.sayAfterFinally(..))")
    public void afterFinallyAdvice() {
        System.out.println("===========after finally advice");
    }

    @Around(value="execution(* cn.javass..*.sayAround(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========around before advice");
        Object retVal = pjp.proceed(new Object[] {"replace"});
        System.out.println("===========around after advice");
        return retVal;
    }
    
    @DeclareParents(value="cn.javass..*.IHelloWorldService+", defaultImpl=cn.javass.spring.chapter6.service.impl.IntroductiondService.class)
    private IIntroductionService introductionService;
    
    @Around(value="this(cn.javass.spring.chapter6.service.IIntroductionService+)")
    public Object thisPointcut(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========around sssss advice");
        Object retVal = pjp.proceed();
        System.out.println("===========around ssss advice");
        return retVal;
    }
}
