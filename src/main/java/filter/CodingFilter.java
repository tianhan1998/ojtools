package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CodingFilter
 */
public class CodingFilter implements Filter {

    /**
     * Default constructor.
     */
    public CodingFilter() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest re = (HttpServletRequest) request;
	HttpServletResponse rs = (HttpServletResponse) response;
	String spath = re.getServletPath();
	String[] urls = { "/login", "/json", ".js", ".css", ".ico", ".jpg", ".png" };
	boolean flag = true;
	for (String str : urls) {
	    if (spath.indexOf(str) != -1) {
		flag = false;
		break;
	    }
	}
	if (flag) {
	    re.setCharacterEncoding("UTF-8");
	    rs.setContentType("text/html;charset=utf-8");
	}
	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	// TODO Auto-generated method stub
    }

}
