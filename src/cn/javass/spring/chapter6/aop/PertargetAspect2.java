package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;

import cn.javass.spring.chapter6.service.IIntroductionService;

@Aspect("pertarget(target(cn.javass.spring.chapter6.service.IPointcutService))")
public class PertargetAspect2 {
   
    private int counter = 1;
    

    @DeclareParents(value = "cn.javass..IPointcutService+", defaultImpl = cn.javass.spring.chapter6.service.impl.IntroductiondService.class)
    private IIntroductionService introductionService;
    
    @Before(value = "execution(public * *(..))")
    public void executionTest1(JoinPoint jp) {
        System.out.println("============pertarget(target(cn.javass.spring.chapter6.service.IPointcutService)):" + counter++);
    }
    
}
