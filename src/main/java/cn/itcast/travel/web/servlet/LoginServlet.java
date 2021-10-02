package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取参数集合
        Map<String, String[]> map = request.getParameterMap();
        //封装User对象；
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用方法实现登录
        UserService service=new UserServiceImpl();
        User u=service.login(user);
        //判断用户密码是否存在
        ResultInfo info=new ResultInfo();
        if (u==null){
            //错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码错误，请检查！");
        }
        //判断是否激活
        if (u!=null&&!"Y".equals(u.getStatus())){
            //用户没有激活
            info.setFlag(false);
            info.setErrorMsg("该用户尚未激活，请前往邮箱进行激活！");

        }
        //判断登录成功
        if (u!=null&&"Y".equals(u.getStatus())){

            info.setFlag(true);
        }
        //响应数据

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
        request.getSession().setAttribute("user",user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
