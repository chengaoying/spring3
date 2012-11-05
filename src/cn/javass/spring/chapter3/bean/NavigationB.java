package cn.javass.spring.chapter3.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class NavigationB {
    
    private NavigationC navigationC;
    
    private List<NavigationC> list;

    private Properties properties;
    
    private NavigationC[] array = new NavigationC[1];

    private Map<String, NavigationC> map;
    
    public void setNavigationC(NavigationC navigationC) {
        this.navigationC = navigationC;
    }
    public NavigationC getNavigationC() {
        return navigationC;
    }
    public void setList(List<NavigationC> list) {
        this.list = list;
    }
    public List<NavigationC> getList() {
        return list;
    }
    
    public void setMap(Map<String, NavigationC> map) {
        this.map = map;
    }
    
    public Map<String, NavigationC> getMap() {
        return map;
    }
    public Properties getProperties() {
        return properties;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public NavigationC[] getArray() {
        return array;
    }
    public void setArray(NavigationC[] array) {
        this.array = array;
    }
    
}

