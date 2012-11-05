package cn.javass.spring.chapter4.bean;

import org.springframework.core.io.ResourceLoader;

public class ResourceBean2 {
    
    private ResourceLoader resourceLoader;
    
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
