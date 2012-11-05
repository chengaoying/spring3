package cn.javass.spring.chapter12.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import cn.javass.spring.chapter2.helloworld.HelloApi;
import cn.javass.spring.chapter3.HelloImpl3;
import cn.javass.spring.chapter3.HelloImpl4;
import cn.javass.spring.chapter3.HelloImpl5;
import cn.javass.spring.chapter3.bean.Printer;

@Configuration("ctxConfig") //1、使用@Configuration注解配置类
@ImportResource("classpath:chapter12/configuration/importResource.xml")
public class ApplicationContextConfig {
    
    @Bean     //2、使用@Bean注解方法Bean对象的方法
    public String message() {
        return "hello";
    }
    
    
    @Bean
    public HelloApi helloImpl3() {
        //通过构造器注入,分别是引用注入（message()）和常量注入（1）
        return new HelloImpl3(message(), 1);
    }
    
    @Bean 
    public HelloApi helloImpl4() {
        HelloImpl4 helloImpl4 = new HelloImpl4();
        //通过setter注入注入引用
        helloImpl4.setMessage(message());
        //通过setter注入注入常量
        helloImpl4.setIndex(1);
        return helloImpl4;
    }
    
    @Bean
    @Scope("singleton")
    public HelloApi helloApi2() {
        HelloImpl5 helloImpl5 = new HelloImpl5() {
            @Override
            public Printer createPrototypePrinter() {
                //方法注入,注入原型Bean
                return prototypePrinter();
            }
            
            @Override
            public Printer createSingletonPrinter() {
                //方法注入,注入单例Bean
                return singletonPrinter();
            }
        };
        //依赖注入,注入单例Bean
        helloImpl5.setPrinter(singletonPrinter());
        return helloImpl5;
    }
    
    @Bean
    @Scope(value="prototype")
    public Printer prototypePrinter() {
        return new Printer();
    }

    @Bean
    @Scope(value="singleton")
    public Printer singletonPrinter() {
        return new Printer();
    }
    
    
    
}
