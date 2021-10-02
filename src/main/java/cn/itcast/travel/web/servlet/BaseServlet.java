package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求路径
        String uri = request.getRequestURI();
        //获取方法名
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("方法名称"+methodName);
        //获取方法对象
        try {
            //忽略访问权限控制符
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            //method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
   //将传入的对象序列化为json，并写回客户端
   public void writeValue(Object object,HttpServletResponse response) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       response.setContentType("application/json;charset=utf-8");
       mapper.writeValue(response.getOutputStream(),object);
   }
   public String writeValueAsStream(Object object) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       //response.setContentType("application/json;charset=utf-8");
       String s = mapper.writeValueAsString(object);
       return s;

   }

        /*其它方法定义*/

        /**
         * 字符串数字类型转换方法
         *      将数字字符串转换为数字，例如："5" -> 5
         * @param numStr 数字字符串
         * @return 整型数字
         * @throws ClassCastException
         *      类型转换异常，避免传入的字符串内容不是数字
         */
        protected int parseInt(String numStr, int defaultValue) throws ClassCastException {
            return (numStr != null && numStr.length() > 0 && !"null".equalsIgnoreCase(numStr) && !"NaN".equalsIgnoreCase(numStr))
                    ? Integer.parseInt(numStr) : defaultValue;      // 注意：浏览器提交的空值到后台会被识别为<字符串"null">
        }

        /*其它方法定义*/
    }



