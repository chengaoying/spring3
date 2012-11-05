package cn.javass.spring.chapter10;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebFrameWorkIntegrateTest {
            
    @Test
    public void webFrameWorkTest() throws Exception {
        Server server = new Server(8080);
        WebAppContext webapp = new WebAppContext();
        webapp.setResourceBase("webapp");
        //webapp.setDescriptor("webapp/WEB-INF/web.xml");
        webapp.setContextPath("/");
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webapp);
        server.start();
        server.join();
        //server.stop();
    }
}
