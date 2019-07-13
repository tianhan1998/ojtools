package servlet;

import entity.Problem;
import service.ProblemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "addProblemServlet")
public class addProblemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        int count=Integer.parseInt(request.getParameter("count"));
        ProblemService ps=new ProblemService();
        try {
            List<Problem> list= (List<Problem>) request.getSession().getAttribute("list");
            boolean stat=ps.addProblem(id);
            if(stat){
                list.get(count).setStatus(1);
                Boolean com=true;
                for (Problem p:list) {
                    if(p.getStatus()==0){
                        com=false;
                        break;
                    }
                }
                request.getSession().setAttribute("list",list);
                String datenow= (String) request.getSession().getAttribute("datenow");
                if(datenow.equals(list.get(0).getDate())==false&&com==true) {//如果正在处理的题目不是今天的（昨天没做完）特殊处理标志
                    Cookie cookie = new Cookie("vip", "true");
                    Cookie cookie1=new Cookie("vipdate",new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()));
                    cookie.setMaxAge(60*60*24);//存一天
                    cookie1.setMaxAge(60*60*24);
                    cookie.setPath(request.getContextPath());
                    cookie1.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    response.addCookie(cookie1);
                }
            }else{
                request.getSession().setAttribute("message","添加失败");
            }
            response.sendRedirect("listtask.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
