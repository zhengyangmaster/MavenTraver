package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
   private CategoryService service= new CategoryServiceImpl();
    public void findAll (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //1.查询所有对象
        List<Category> list = service.findCategory();
        //2.将结果序列化并返回
        /*ObjectMapper mapper = new ObjectMapper();
            //设置响应格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),list);*/
        writeValue(list,response);

    }



}
