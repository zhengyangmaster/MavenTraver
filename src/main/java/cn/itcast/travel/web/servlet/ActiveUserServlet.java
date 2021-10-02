package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //调用service 完成激活
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);
            String msg=null;
            //判断标记
            if (flag){
                //激活成功
                msg="激活成功请登录！<a href='login.html'>登录</a>";
            }else{

                msg="激活失败，傻逼了吧";

            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
