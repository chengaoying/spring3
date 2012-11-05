package cn.javass.spring.chapter3;

import java.util.HashMap;
import java.util.Map;

public class BeanDifinitionRegister {
    
    //bean定义缓存，此处不考虑并发问题
    private final Map<String, BeanDefinition> DEFINITIONS = new HashMap<String, BeanDefinition>();

    //注册Bean定义
    public void registerBeanDefinition(String beanName, BeanDefinition bd) {
        //1.本实现不允许覆盖Bean定义
        if(DEFINITIONS.containsKey(bd.getId())) {
            throw new RuntimeException("已存在Bean定义，此实现不允许覆盖");
        }
        //2.将Bean定义放入Bean定义缓存池
        DEFINITIONS.put(bd.getId(), bd);
    }

    //获取Bean定义
    public BeanDefinition getBeanDefinition(String beanName) {
        return DEFINITIONS.get(beanName);
    }

    //是否包含Bean定义
    public boolean containsBeanDefinition(String beanName) {
        return DEFINITIONS.containsKey(beanName);
    }
    
    
}
