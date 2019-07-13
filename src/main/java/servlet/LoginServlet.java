package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        request.getSession().setAttribute("username",username);
        String name[]={"朱森林","丁世杰","田涵","闫兴东","王元帅","岳阳","姜兴轮","陈诗毅","黄智超","张浩","张振强","张雨欣","赵明柯","张鹏","李富龙","郭雨鹏","马海力","程钰杰"};
        for(int i=0;i<name.length;i++){
            if(username.equals(name[i])) {
                if("true".equals(request.getParameter("autologin"))) {
                    Cookie cookie = new Cookie("username", username);
                    cookie.setPath(request.getContextPath());
                    cookie.setMaxAge(3600 * 24 * 30);
                    response.addCookie(cookie);
                }
                request.getRequestDispatcher("/GetProblemServlet").forward(request,response);
            }
        }
        response.getWriter().write("<script language='javascript'>alert('姓名错误!!');window.location.href='login.jsp';</script>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
