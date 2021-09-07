import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet("/hi")
public class Login extends HttpServlet {

    public void init(ServletConfig config) {
        System.out.println("init");
    }



    public void service(
            ServletRequest req,
            ServletResponse res)
            throws ServletException, IOException {
        System.out.println("service");
    }

    public void destroy() {
        System.out.println("destroy");
    }


    public ServletConfig getServletConfig() {
        return null;
    }

    public String getServletInfo() {
        return "";
    }

}
