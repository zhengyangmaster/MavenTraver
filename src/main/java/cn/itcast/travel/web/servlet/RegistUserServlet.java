package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
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

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //验证码校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较两个验证码
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){

            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请输入正确验证码！！！");
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            //将数据写到前端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);
            return;
        }


        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册
        UserService service = new UserServiceImpl();
        boolean flag= service.regist(user);

        ResultInfo info = new ResultInfo();
        //响应数据
        if (flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败了");
        }
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(info);
        //将数据写到前端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
