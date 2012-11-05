package cn.javass.spring.chapter6.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cn.javass.spring.chapter6.Model;
import cn.javass.spring.chapter6.Secure;

@Secure
public interface IPointcutService {
    
    public boolean test();


    public boolean test(Object obj);

    public boolean test(Date date);
    
    public Model test(Model model);

    public void test(@Secure String str1, @Secure String str2);

    public void test(@Secure Model model1, @Secure Model model2);
    

    public void test(Map<Model, Model> map);

    public void test(Map map, int i);

    public void test(HashMap<Model, Model> map, String str);

    public void test(Collection<Model> collection);

    public void test(Set<TestMap> set);
    
    public void test(Stack<Map> list);
    
    public static class TestMap extends HashMap {}


}
