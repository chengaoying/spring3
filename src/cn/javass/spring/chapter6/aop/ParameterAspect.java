package cn.javass.spring.chapter6.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import cn.javass.spring.chapter6.Secure;
import cn.javass.spring.chapter6.service.IPointcutService;

@Aspect
public class ParameterAspect {
  
    @Before(value="execution(* sayBefore(*))")
    public void before(JoinPoint jp) {
        System.out.println("==kind:" + jp.getKind());
        System.out.println("==target:" + jp.getTarget());
        System.out.println("==this:" + jp.getThis());
        System.out.println("==args:" + jp.getArgs()[0]);
        System.out.println("==signature:" + jp.getSignature());
        System.out.println("==toString:" + jp.getStaticPart().toString());
        System.out.println("==toShortString:" + jp.getStaticPart().toShortString());
        System.out.println("==toLongString:" + jp.getStaticPart().toLongString());
        
    }
    
    @Before(value="execution(* sayBefore(*))")
    public void before(JoinPoint.StaticPart jp) {
        System.out.println("===========JoinPoint.StaticPart==========");
        System.out.println("==kind:" + jp.getKind());
        System.out.println("==signature:" + jp.getSignature());
        System.out.println("==toString:" + jp.toString());
        System.out.println("==toShortString:" + jp.toShortString());
        System.out.println("==toLongString:" + jp.toLongString());
        
    }

    @Before(value="execution(* test(*)) && args(param)", argNames="param")
    public void before1(String param) {
        System.out.println("===param1:" + param);
    }

    @Before(value="execution(* test(*)) && args(param)")
    public void before2(String param) {
        System.out.println("===param2:" + param);
    }

    @Before(value="args(param)")
    public void before3(String param) {
        System.out.println("===param3:" + param);
    }
    @Before(value="args(param)")
    public void before4(JoinPoint jp, String param) {
        System.out.println("===param4:" + param);
    }

    @Before(value="args(param) && target(bean) && @annotation(secure)", 
            argNames="jp,param,bean,secure")
    public void before5(JoinPoint jp, String param, IPointcutService pointcutService, Secure secure) {
        System.out.println("===param5:" + param);
        System.out.println("===target:" + pointcutService);
        System.out.println("===annotation:" + secure);
    }
    
    
    @Pointcut(value="args(param)", argNames="param")
    private void pointcut1(String param){}

    @Pointcut(value="@annotation(secure)", argNames="secure")
    private void pointcut2(Secure secure){}
    
    @Before(value = "pointcut1(param) && pointcut2(secure)", argNames="param, secure")
    public void before6(JoinPoint jp, String param, Secure secure) {
        System.out.println("===param6:" + param);
        System.out.println("===annotation:" + secure);
    }
    
}
