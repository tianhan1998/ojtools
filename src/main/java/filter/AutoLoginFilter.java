package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        Cookie[] cookies=request.getCookies();
        Cookie findc=null;
        if(cookies!=null){
            for (Cookie c:cookies
                 ) {
                if(c.getName().equals("username")&&c.getValue()!=null){
                    findc=c;
                    break;
                }
            }
            if(findc!=null) {
                request.getSession().setAttribute("username", findc.getValue());
                response.sendRedirect(request.getContextPath() + "/GetProblemServlet");
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
