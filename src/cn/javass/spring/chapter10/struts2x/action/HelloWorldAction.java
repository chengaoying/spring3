package cn.javass.spring.chapter10.struts2x.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorldAction extends ActionSupport {
    
    private String message;
    
    @Override
    public String execute() throws Exception {
        ServletActionContext.getRequest().setAttribute("message", message);
        return "hello";
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
