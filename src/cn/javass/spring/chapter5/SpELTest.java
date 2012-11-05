package cn.javass.spring.chapter5;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {
    
    @Test
    public void helloWorld() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        Assert.assertEquals("Hello World!", expression.getValue(context));
    }
    
    @Test
    public void testParserContext() {
        ExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }
            @Override
            public String getExpressionPrefix() {
                return "#{";
            }
            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        String template = "#{'Hello '}#{'World!'}";
        Expression expression = parser.parseExpression(template, parserContext);
        Assert.assertEquals("Hello World!", expression.getValue());
    }
    
    @Test
    public void testBasicExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.字符串
        String str1 = parser.parseExpression("'Hello World!'").getValue(String.class);
        String str2 = parser.parseExpression("\"Hello World!\"").getValue(String.class);
        Assert.assertEquals(str1, str2);
        
        int int1 = parser.parseExpression("1").getValue(Integer.class);//int类型
        Assert.assertEquals(1, int1);

        long long1 = parser.parseExpression("-1L").getValue(long.class);//long类型
        Assert.assertEquals(-1L, long1);

        float float1 = parser.parseExpression("1.1").getValue(Float.class);//float类型
        Assert.assertEquals(1.1f, float1);
        
        double double1 = parser.parseExpression("1.1E+2").getValue(double.class);//double类型
        Assert.assertEquals(1.1E+2, double1);
        
        int hex1 = parser.parseExpression("0xa").getValue(Integer.class);//16进制int类型
        Assert.assertEquals(0xa, hex1);

        long hex2 = parser.parseExpression("0xaL").getValue(long.class);//16进制long类型
        Assert.assertEquals(0xaL, hex2);
        
        boolean true1 = parser.parseExpression("true").getValue(boolean.class);//布尔类型
        Assert.assertEquals(true, true1);

        boolean false1 = parser.parseExpression("false").getValue(boolean.class);//布尔类型
        Assert.assertEquals(false, false1);
        
        Object null1 = parser.parseExpression("null").getValue(Object.class);//null类型
        Assert.assertEquals(null, null1);
        
    }
    
    @Test
    public void testArithmeticExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        int result1 = parser.parseExpression("1+2-3*4/2").getValue(Integer.class);
        Assert.assertEquals(-3, result1);

        int result2 = parser.parseExpression("4%3").getValue(Integer.class);
        Assert.assertEquals(1, result2);


        int result3 = parser.parseExpression("2^3").getValue(Integer.class);
        Assert.assertEquals(8, result3);

        int result4 = parser.parseExpression("6 DIV 2").getValue(Integer.class);
        Assert.assertEquals(3, result4);

        int result5 = parser.parseExpression("4 MOD 3").getValue(Integer.class);
        Assert.assertEquals(1, result5);
    }
    
    @Test
    public void testRelationalExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        boolean result1 = parser.parseExpression("1 gt 2").getValue(boolean.class);
        Assert.assertEquals(false, result1);
        
        boolean result2 = parser.parseExpression("1 between {1, 2}").getValue(boolean.class);
        Assert.assertEquals(true, result2);
    }

    @Test
    public void testLogicalExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "2>1 and (!true or !false)";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        Assert.assertEquals(true, result1);

        String expression2 = "2>1 and (NOT true or NOT false)";
        boolean result2 = parser.parseExpression(expression2).getValue(boolean.class);
        Assert.assertEquals(true, result2);
    }

    @Test
    public void testStringConcatExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "'Hello ' + 'World!'";
        String result1 = parser.parseExpression(expression1).getValue(String.class);
        Assert.assertEquals("Hello World!", result1);

        String expression2 = "'Hello World!'[0]";
        String result2 = parser.parseExpression(expression2).getValue(String.class);
        Assert.assertEquals("H", result2);
        
    }
    
    
    @Test
    public void testTernaryExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "2>1?true:false";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        Assert.assertEquals(true, result1);

        String expression2 = "null?:false";
        boolean result2 = parser.parseExpression(expression2).getValue(boolean.class);
        Assert.assertEquals(false, result2);

        String expression3 = "true?:false";
        boolean result3 = parser.parseExpression(expression3).getValue(boolean.class);
        Assert.assertEquals(true, result3);
        
    }

    @Test
    public void testRegexExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String expression1 = "'123' matches '\\d{3}'";
        boolean result1 = parser.parseExpression(expression1).getValue(boolean.class);
        Assert.assertEquals(true, result1);
        
    }

    @Test
    public void testClassTypeExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //java.lang包类访问
        Class<String> result1 = parser.parseExpression("T(String)").getValue(Class.class);
        Assert.assertEquals(String.class, result1);

        //其他包类访问
        String expression2 = "T(cn.javass.spring.chapter5.SpELTest)"; 
        Class<String> result2 = parser.parseExpression(expression2).getValue(Class.class);
        Assert.assertEquals(SpELTest.class, result2);
        
        //类静态字段访问
        int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class);
        Assert.assertEquals(Integer.MAX_VALUE, result3);

        //类静态方法调用
        int result4 = parser.parseExpression("T(Integer).parseInt('1')").getValue(int.class);
        Assert.assertEquals(1, result4);
    }

    @Test
    public void testConstructorExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String result1 = parser.parseExpression("new String('haha')").getValue(String.class);
        Assert.assertEquals("haha", result1);

        Date result2 = parser.parseExpression("new java.util.Date()").getValue(Date.class);
        Assert.assertNotNull(result2);
    }
    

    @Test
    public void testInstanceofExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        boolean result1 = parser.parseExpression("'haha' instanceof T(String)").getValue(boolean.class);
        Assert.assertEquals(true, result1);
    }

    @Test
    public void testVariableExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("variable", "haha");
        String result1 = parser.parseExpression("#variable").getValue(context, String.class);
        Assert.assertEquals("haha", result1);

        context = new StandardEvaluationContext("haha");
        String result2 = parser.parseExpression("#root").getValue(context, String.class);
        Assert.assertEquals("haha", result2);

        String result3 = parser.parseExpression("#this").getValue(context, String.class);
        Assert.assertEquals("haha", result3);
    }
    
    

    @Test
    public void testFunctionExpression() throws SecurityException, NoSuchMethodException {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        context.registerFunction("parseInt", parseInt);
        context.setVariable("parseInt2", parseInt);
        String expression1 = "#parseInt('3') == #parseInt2('3')";
        boolean result1 = parser.parseExpression(expression1).getValue(context, boolean.class);
        Assert.assertEquals(true, result1);
        
    }
    
    @Test
    public void testAssignExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.给root对象赋值
        EvaluationContext context = new StandardEvaluationContext("aaaa");
        String result1 = parser.parseExpression("#root='aaaaa'").getValue(context, String.class);
        Assert.assertEquals("aaaaa", result1);
        String result2 = parser.parseExpression("#this='aaaa'").getValue(context, String.class);
        Assert.assertEquals("aaaa", result2);

        //2.给自定义变量赋值
        context.setVariable("#variable", "variable");
        String result3 = parser.parseExpression("#variable=#root").getValue(context, String.class);
        Assert.assertEquals("aaaa", result3);
    }

    @Test
    public void testPropertyExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        //1.访问root对象属性
        Date date = new Date();
        StandardEvaluationContext context = new StandardEvaluationContext(date);
        int result1 = parser.parseExpression("Year").getValue(context, int.class);
        Assert.assertEquals(date.getYear(), result1);
        int result2 = parser.parseExpression("year").getValue(context, int.class);
        Assert.assertEquals(date.getYear(), result2);
        
        //2.安全访问
        context.setRootObject(null);
        Object result3 = parser.parseExpression("#root?.year").getValue(context, Object.class);
        Assert.assertEquals(null, result3);
        
        //3.给root对象属性赋值
        context.setRootObject(date);
        int result4 = parser.parseExpression("Year = 4").getValue(context, int.class);
        Assert.assertEquals(4, result4);
        parser.parseExpression("Year").setValue(context, 5);
        int result5 = parser.parseExpression("Year").getValue(context, int.class);
        Assert.assertEquals(5, result5);
    }
    

    
    @Test
    public void testMethodExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        String result1 = parser.parseExpression("'haha'.substring(2,4)").getValue(String.class);
        Assert.assertEquals("ha", result1);
        Date date = new Date();
        StandardEvaluationContext context = new StandardEvaluationContext(date);
        int result2 = parser.parseExpression("getYear()").getValue(context, int.class);
        Assert.assertEquals(date.getYear(), result2);
    }

    @Test
    public void testBeanExpression() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.refresh();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(ctx));
        Properties result1 = parser.parseExpression("@systemProperties").getValue(context, Properties.class);
        Assert.assertEquals(System.getProperties(), result1);
    }
    
    
    
    
    

    @Test
    public void testInnerListExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        List<Integer> result1 = parser.parseExpression("{1,2,3}").getValue(List.class);
        Assert.assertEquals(new Integer(1), result1.get(0));
        try {
            result1.set(0, 2);
            //不可能执行到这，对于字面量列表不可修改
            Assert.fail();
        } catch (Exception e) {
        }
        List<Integer> result2 = parser.parseExpression("{}").getValue(List.class);
        Assert.assertEquals(0, result2.size());

        String expression3 = "{{1+2,2+4},{3,4+4}}";
        List<List<Integer>> result3 = parser.parseExpression(expression3).getValue(List.class);
        result3.get(0).set(0, 1);
        Assert.assertEquals(2, result3.size());
    }
    

    @Test
    public void testInnerArrayExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        int[] result1 = parser.parseExpression("new int[1]").getValue(int[].class);
        Assert.assertEquals(1, result1.length);
        int[] result2 = parser.parseExpression("new int[2]{1,2}").getValue(int[].class);
        Assert.assertEquals(2, result2[1]);
        
        String expression3 = "new int[1][2][3]";
        int[][][] result3 = parser.parseExpression(expression3).getValue(int[][][].class);
        Assert.assertEquals(3, result3[0][0].length);

        String expression4 = "new int[1][2][3]{{1}{2}{3}}";
        try {
            int[][][] result4 = parser.parseExpression(expression4).getValue(int[][][].class);
            Assert.fail();
        } catch (Exception e) {
        }
    }    

    @Test
    public void testListAndMapGetValueExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        int result1 = parser.parseExpression("{1,2,3}[0]").getValue(int.class);
        //即list.get(0)
        Assert.assertEquals(1, result1);
        
        Collection<Integer> collection = new HashSet<Integer>();
        collection.add(1);
        collection.add(2);
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("collection", collection);
        int result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
        //对于任何集合类型通过Iterator来定位元素
        Assert.assertEquals(2, result2);
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        EvaluationContext context3 = new StandardEvaluationContext();
        context3.setVariable("map", map);
        int result3 = parser.parseExpression("#map['a']").getValue(context3, int.class);
        Assert.assertEquals(1, result3);
    }    

    @Test
    public void testListAndMapSetValueExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        
        //1.修改数组元素值
        int[] array = new int[] {1, 2};
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("array", array);
        int result1 = parser.parseExpression("#array[1] = 3").getValue(context1, int.class);
        Assert.assertEquals(3, result1);
        
        
        //2.修改集合值
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(1);
        collection.add(2);
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("collection", collection);
        int result2 = parser.parseExpression("#collection[1] = 3").getValue(context2, int.class);
        Assert.assertEquals(3, result2);
        parser.parseExpression("#collection[1]").setValue(context2, 4);
        result2 = parser.parseExpression("#collection[1]").getValue(context2, int.class);
        Assert.assertEquals(4, result2);
        
        
        //3.修改map元素值
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        EvaluationContext context3 = new StandardEvaluationContext();
        context3.setVariable("map", map);
        int result3 = parser.parseExpression("#map['a'] = 2").getValue(context3, int.class);
        Assert.assertEquals(2, result3);
     
    }    

    @Test
    public void testProjectionExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        
        
        //1.首先准备测试数据
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);
        collection.add(5);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
            
        //2.测试集合
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("collection", collection);
        Collection<Integer> result1 = parser.parseExpression("#collection.![#this+1]").getValue(context1, Collection.class);
        Assert.assertEquals(2, result1.size());
        Assert.assertEquals(new Integer(5), result1.iterator().next());
        
        
        
        //3.测试字典
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("map", map);
        List<Integer> result2 = parser.parseExpression("#map.![value+1]").getValue(context2, List.class);
        Assert.assertEquals(2, result2.size());
    }    

    @Test
    public void testSelectExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        
        //1.准备数据
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);
        collection.add(5);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        
        //2.集合或数组测试
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("collection", collection);
        Collection<Integer> result1 = parser.parseExpression("#collection.?[#this>4]").getValue(context1, Collection.class);
        Assert.assertEquals(1, result1.size());
        Assert.assertEquals(new Integer(5), result1.iterator().next());
        
        //3.字典测试
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("map", map);
        Map<String, Integer> result2 = parser.parseExpression("#map.?[#this.key != 'a']").getValue(context2, Map.class);
        Assert.assertEquals(1, result2.size());

        List<Integer> result3 = parser.parseExpression("#map.?[key != 'a'].![value+1]").getValue(context2, List.class);
        Assert.assertEquals(new Integer(3), result3.iterator().next());
    }    

    
    @Test
    public void testTemplateExpression() {
        SpelExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }
            @Override
            public String getExpressionPrefix() {
                return "${";
            }
            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("v0", 1);
        context.setVariable("v1", 2);
        String result = parser.parseExpression("Error ${#v0} ${#v1}", parserContext).getValue(context, String.class);
        Assert.assertEquals("Error 1 2", result);
        
    }    
    

    
    @Test
    public void testXmlExpression() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter5/el1.xml");
        String hello1 = ctx.getBean("hello1", String.class);
        String hello2 = ctx.getBean("hello2", String.class);
        String hello3 = ctx.getBean("hello3", String.class);
        Assert.assertEquals("Hello World!", hello1);
        Assert.assertEquals("Hello World!", hello2);
        Assert.assertEquals("Hello World!", hello3);
    }    
    

    @Test
    public void testAnnotationExpression() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter5/el2.xml");
        SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
        Assert.assertEquals("Hello World!", helloBean1.getValue());

        SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
        Assert.assertEquals("haha", helloBean2.getValue());
    }    

    @Test
    public void testPrefixExpression() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("chapter5/el3.xml");
        SpELBean helloBean1 = ctx.getBean("helloBean1", SpELBean.class);
        Assert.assertEquals("#{'Hello' + world}", helloBean1.getValue());
        
        SpELBean helloBean2 = ctx.getBean("helloBean2", SpELBean.class);
        Assert.assertEquals("Hello World!", helloBean2.getValue());
    }    
    
    
}
