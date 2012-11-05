package cn.javass.spring.chapter10.struts1x.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HelloWorldAction3 extends Action {
    
    private String message;
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("message", message);
        return mapping.findForward("hello");
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
