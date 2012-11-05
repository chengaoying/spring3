package cn.javass.spring.chapter10.struts1x.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.struts.ActionSupport;

public class HelloWorldAction2 extends ActionSupport {
    
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebApplicationContext ctx = getWebApplicationContext();     
        String message = ctx.getBean("message", String.class); 

        request.setAttribute("message", message);
        return mapping.findForward("hello");
    }
}
