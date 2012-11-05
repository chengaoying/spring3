package cn.javass.spring.chapter10.struts1x.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class HelloWorldAction1 extends Action {
    
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        WebApplicationContext ctx = WebApplicationContextUtils.
            getRequiredWebApplicationContext(getServlet().getServletContext());        
        String message = ctx.getBean("message", String.class); 

        request.setAttribute("message", message);
        return mapping.findForward("hello");
    }
}
