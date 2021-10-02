package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *
 **/
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService Userservice = new UserServiceImpl();

    //注册方法
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较两个验证码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {

            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请输入正确验证码！！！");
        /*    ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            //将数据写到前端
            response.setContentType("application/json;charset=utf-8");*/
            String s = writeValueAsStream(info);
            response.getWriter().write(s);
            return;
        }


        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册

        boolean flag = Userservice.regist(user);

        ResultInfo info = new ResultInfo();
        //响应数据
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败了");
        }
        //将info对象序列化为json
//        ObjectMapper mapper = new ObjectMapper();
//        String s = mapper.writeValueAsString(info);
//        //将数据写到前端
//        response.setContentType("application/json;charset=utf-8");
        String s = writeValueAsStream(info);
        response.getWriter().write(s);

    }

    //激活方法
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //调用service 完成激活
            //UserService userService = new UserServiceImpl();
            boolean flag = Userservice.active(code);
            String msg = null;
            //判断标记
            if (flag) {
                //激活成功
                msg = "激活成功请登录！<a href='/travel/login.html'>登录</a>";
            } else {

                msg = "激活失败，傻逼了吧";

            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    //查找一个用户方法
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        System.out.println(user);
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user, response);
    }

    //登录方法
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数集合
        Map<String, String[]> map = request.getParameterMap();
        //封装User对象；
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用方法实现登录
        //UserService service=new UserServiceImpl();
        User u = Userservice.login(user);
        //判断用户密码是否存在
        ResultInfo info = new ResultInfo();
        if (u == null) {
            //错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码错误，请检查！");
        }
        //判断是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户没有激活
            info.setFlag(false);
            info.setErrorMsg("该用户尚未激活，请前往邮箱进行激活！");

        }
        //判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {

            info.setFlag(true);
        }
        //响应数据

      /*  ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);*/
        writeValue(info, response);
        request.getSession().setAttribute("user", user);
    }

    //退出登录方法
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁Sessions对象
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/login.html");
    }


}



