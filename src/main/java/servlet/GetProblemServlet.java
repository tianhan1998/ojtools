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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "GetProblemServlet")
public class GetProblemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String username= (String) request.getSession().getAttribute("username");
                ProblemService ps = new ProblemService();
                Date date=new Date();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String datenow=simpleDateFormat.format(date.getTime());
                request.getSession().setAttribute("datenow",datenow);
                List<Problem>originProblemList=null;
                Cookie[] cookies=request.getCookies();
                Cookie findc=null;
                Cookie vip=null;
                Cookie vipdate=null;
                for (Cookie c:cookies) {
                    if(c.getName().equals("lasttime")){
                        findc=c;
                    }
                    if(c.getName().equals("vip")){
                        vip=c;
                    }
                    if(c.getName().equals("vipdate")){
                        vipdate=c;
                    }
                }
                if(vip!=null){//如果是vip
                    if(vipdate.getValue().equals(datenow)==false){//vip过期
                        Cookie cookie=new Cookie("vip",null);
                        Cookie cookie1=new Cookie("vipdate",null);
                        cookie.setPath(request.getContextPath());
                        cookie1.setPath(request.getContextPath());
                        cookie.setMaxAge(0);
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie);
                        response.addCookie(cookie1);
                    }
                }
                if(findc!=null){//是否为第一次登陆
                    originProblemList=ps.findByName(findc.getValue(),username);
                    Boolean flag=true;//检测上一次是否全部完成
                    for(int i=0;i<originProblemList.size();i++){
                        if(originProblemList.get(i).getStatus()==0){
                            flag=false;
                            break;
                        }
                    }
                    if(flag){//如果全做完了，查看是否是当天重复打开网页了(转发检测）
                        if(findc.getValue().equals(datenow)==false&&vip==null){//如果不是当天
                            originProblemList =ps.findByName(datenow,username);
                        }
                    }
                }
                else {//否则找今天的（一定为空）
                    originProblemList = ps.findByName(datenow, username);
                }
                    if (originProblemList.size() == 0) {//如果未找到记录（没有分配题)
                        List<Problem> newlist = new ArrayList<Problem>();
                        originProblemList = ps.getProblemList();
                        Cookie cookie=new Cookie("lasttime",datenow);//只有重新分配5道题后才会写入cookie
                        cookie.setPath(request.getContextPath());
                        cookie.setMaxAge(60*60*24*30);
                        response.addCookie(cookie);
                        for (int i = 0; i < 5 && i < originProblemList.size(); i++) {//从题库中找5个未被人标注的未导入的题
                            ps.setName(datenow, username, originProblemList.get(i).getId());//数据库分配姓名和日期
                            originProblemList.get(i).setDate(datenow);//实体类里写日期
                            newlist.add(originProblemList.get(i));
                        }
                        request.getSession().setAttribute("list", newlist);
                        response.sendRedirect("listtask.jsp");
                    } else {//不需要分配（有剩余或者已经做完）
                        request.getSession().setAttribute("list", originProblemList);
                        response.sendRedirect("listtask.jsp");
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
