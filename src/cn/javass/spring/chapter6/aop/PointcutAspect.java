package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;

import cn.javass.spring.chapter6.service.IIntroductionService;

@Aspect
public class PointcutAspect {
        
    /*1、execution*/
    
    @Before(value = "execution(public * *(..))")
    public void executionTest1(JoinPoint jp) {
        dump("execution(public * *(..)", jp);
    }

    @Before(value = "execution(* cn.javass..IPointcutService.*())")
    public void executionTest2(JoinPoint jp) {
        dump("execution(* cn.javass..IPointcutService.*())", jp);
    }
    
    @Before(value = "execution(* cn.javass..*.*(..))")
    public void executionTest3(JoinPoint jp) {
        dump("execution(* cn.javass..*.*(..))", jp);
    }

    @Before(value = "execution(* cn.javass..IPointcutService.*(*))")
    public void executionTest4(JoinPoint jp) {
        dump("execution(* cn.javass..IPointcutService.*(*))", jp);
    }   
    @Before(value = "execution(* (!cn.javass..IPointcutService+).*(..))")
    public void executionTest5(JoinPoint jp) {
        //如果!cn.javass..IPointcutService+ 不加“+”将匹配不到
        dump("execution(* (!cn.javass..IPointcutService+).*(..))", jp);
    }


    @Before(value = "execution(* cn.javass..IPointcutService+.*())")
    public void executionTest6(JoinPoint jp) {
        dump("execution(* cn.javass..IPointcutService+.*())", jp);
    }

    @Before(value = "execution(* cn.javass..IPointcut*.test*(java.util.Date))")
    public void executionTest7(JoinPoint jp) {
        dump("execution(* cn.javass..IPointcut*.test*(java.util.Date))", jp);
    }

    @Before(value = "execution(* cn.javass..IPointcut*.test*(..)  throws IllegalArgumentException, ArrayIndexOutOfBoundsException)")
    public void executionTest8(JoinPoint jp) {
        dump("execution(* cn.javass..IPointcut*.test*(..)  throws IllegalArgumentException, ArrayIndexOutOfBoundsException)", jp);
    }

    @Before(value = "execution(* (cn.javass..IPointcutService+ && java.io.Serializable+).*(..))")
    public void executionTest9(JoinPoint jp) {
        dump("execution(* (cn.javass..IPointcutService+ && java.io.Serializable+).*(..))", jp);
    }

    @Before(value = "execution(@java.lang.Deprecated * *(..))")
    public void executionTest10(JoinPoint jp) {
        dump("execution(@java.lang.Deprecated * *(..))", jp);
    }
    

    @Before(value = "execution(@java.lang.Deprecated @cn.javass..Secure * *(..))")
    public void executionTest11(JoinPoint jp) {
        dump("execution(@java.lang.Deprecated @cn.javass..Secure * *(..))", jp);
    }

    @Before(value = "execution(@(java.lang.Deprecated || cn.javass..Secure) * *(..))")
    public void executionTest12(JoinPoint jp) {
        dump("execution(@(java.lang.Deprecated || cn.javass..Secure) * *(..))", jp);
    }
    
    
    @Before(value = "execution((@cn.javass..Secure *)  *(..))")
    public void executionTest13(JoinPoint jp) {
        dump("execution((@cn.javass..Secure *)  *(..))", jp);
    }

    @Before(value = "execution(*  (@cn.javass..Secure *).*(..))")
    public void executionTest14(JoinPoint jp) {
        dump("execution(*  (@cn.javass..Secure *).*(..))", jp);
    }

    @Before(value = "execution(* *(@cn.javass..Secure (*) , @cn.javass..Secure (*)))")
    public void executionTest15(JoinPoint jp) {
        dump("execution(* *(@cn.javass..Secure (*) , @cn.javass..Secure (*)))", jp);
    }

    @Before(value = "execution(* *((@ cn.javass..Secure *)))")
    public void executionTest16_1(JoinPoint jp) {
        dump("execution(* *((@ cn.javass..Secure *)))", jp);
    }
    @Before(value = "execution(* *(@ cn.javass..Secure *))")
    public void executionTest16_2(JoinPoint jp) {
        dump("execution(* *(@ cn.javass..Secure *))", jp);
    }
    

    @Before(value = "execution(* *(@cn.javass..Secure (@cn.javass..Secure *), @cn.javass..Secure (@cn.javass..Secure *)))")
    public void executionTest17(JoinPoint jp) {
        dump("execution(* *(@cn.javass..Secure (@cn.javass..Secure *), @cn.javass..Secure (@cn.javass..Secure *)))", jp);
    }

    @Before(value = "execution(* *(java.util.Map<cn.javass..Model, cn.javass..Model>, ..))")
    public void executionTest18(JoinPoint jp) {
        dump("execution(* *(java.util.Map<cn.javass..Model, cn.javass..Model>, ..))", jp);
    }

    @Before(value = "execution(* *(java.util.HashMap<cn.javass..Model, cn.javass..Model>, ..))")
    public void executionTest18_1(JoinPoint jp) {
        dump("execution(* *(java.util.HashMap<cn.javass..Model, cn.javass..Model>, ..))", jp);
    }

    @Before(value = "execution(* *(java.util.Collection<@cn.javass..Secure *>))")
    public void executionTest19(JoinPoint jp) {
        dump("execution(* *(java.util.Collection<@cn.javass..Secure *>))", jp);
    }

    @Before(value = "execution(* *(java.util.Set<? extends java.util.HashMap>))")
    public void executionTest20(JoinPoint jp) {
        //不正常工作
        dump("execution(* *(java.util.Set<? extends java.util.HashMap>))", jp);
    }

    @Before(value = "execution(* *(java.util.Stack<? super java.util.HashMap>))")
    public void executionTest21(JoinPoint jp) {
        //不正常工作
        dump("execution(* *(java.util.Stack<? super java.util.HashMap>))", jp);
    }
    
    
    
    @Before(value = "execution(* *(*<@cn.javass..Secure *>))")
    public void executionTest22(JoinPoint jp) {
        //不正常工作
        dump("execution(* *(*<@cn.javass..Secure *>))", jp);
    }
    
    /*2、within*/

    @Before(value = "within(cn.javass..*)")
    public void withinTest1(JoinPoint jp) {
        dump("within(cn.javass..*)", jp);
    }

    @Before(value = "within(cn.javass..IPointcutService+) ")
    public void withinTest2(JoinPoint jp) {
        dump("within(cn.javass..IPointcutService+) ", jp);
    }

    @Before(value = "within(@cn.javass..Secure *)")
    public void withinTest3(JoinPoint jp) {
        dump("within(@cn.javass..Secure *)", jp);
    }
    

    @DeclareParents(value = "cn.javass..IPointcutService+", defaultImpl = cn.javass.spring.chapter6.service.impl.IntroductiondService.class)
    private IIntroductionService introductionService;
    
    /*3、this*/
    @Before(value = "this(cn.javass.spring.chapter6.service.IPointcutService)")
    public void thisTest1(JoinPoint jp) {
        dump("this(cn.javass..IPointcutService)", jp);
    }
    
    @Before(value = "this(cn.javass.spring.chapter6.service.IIntroductionService)")
    public void thisTest2(JoinPoint jp) {
        dump("this(cn.javass..IIntroductionService)", jp);
    }
    
    /*4、target*/
    
    @Before(value = "target(cn.javass.spring.chapter6.service.IPointcutService)")
    public void targetTest1(JoinPoint jp) {
        dump("target(cn.javass..IPointcutService)", jp);
    }
    
    @Before(value = "target(cn.javass.spring.chapter6.service.IIntroductionService)")
    public void targetTest2(JoinPoint jp) {
        //注意与this(cn.javass.spring.chapter6.service.IIntroductionService)区别
        dump("target(cn.javass..IIntroductionService)", jp);
    }
    
    /*5、args*/
    @Before(value = "args(java.io.Serializable, ..)")
    public void argsTest1(JoinPoint jp) {
        dump("args(java.io.Serializable, ..)", jp);
    }
    
    /*6、@within*/
    @Before(value = "@within(cn.javass.spring.chapter6.Secure)")
    public void annotationWithinTest1(JoinPoint jp) {
        dump("@within(cn.javass.spring.chapter6.Secure)", jp);
    }
    /*7、@target*/
    @Before(value = "@target(cn.javass.spring.chapter6.Secure)")
    public void annotationTargetTest1(JoinPoint jp) {
        dump("@target(cn.javass.spring.chapter6.Secure)", jp);
    }
    /*8、@args*/
    @Before(value = "@args(cn.javass.spring.chapter6.Secure)")
    public void annotationArgsTest1(JoinPoint jp) {
        dump("@args(cn.javass.spring.chapter6.Secure)", jp);
    }
    /*9、@annotation*/
    @Before(value = "@annotation(cn.javass.spring.chapter6.Secure)")
    public void annotationAnnotationTest1(JoinPoint jp) {
        dump("@annotation(cn.javass.spring.chapter6.Secure)", jp);
    }
    /*10、bean*/
    @Before(value = "bean(*Service)")
    public void beanTest1(JoinPoint jp) {
        dump("bean(*Service)", jp);
    }
    /*11、reference pointcut*/
    
    @Pointcut(value="bean(*Service)")
    private void pointcut1(){}

    @Pointcut(value="@args(cn.javass.spring.chapter6.Secure)")
    private void pointcut2(){}
    
    @Before(value = "pointcut1() && pointcut2()")
    public void referencePointcutTest1(JoinPoint jp) {
        dump("pointcut1() && pointcut2()", jp);
    }
    

    @Before(value = "cn.javass.spring.chapter6.aop.ReferencePointcutAspect.pointcut()")
    public void referencePointcutTest2(JoinPoint jp) {
        dump("cn.javass.spring.chapter6.aop.ReferencePointcutAspect.pointcut()", jp);
    }
    
    













    private void dump(String expression, JoinPoint jp) {
        System.out.println("=============== [" + expression + "] matches [" + jp.getSignature().toLongString()+ "]");
    }
}