package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service= new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");

        if (rname !=null && rname.length()>0 && "null".equals(rname)){
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }





        //处理参数
        //类别的id


        int  cid= this.parseInt(cidStr,0);


        //当前页面数
        int currentPage=this.parseInt(currentPageStr,1);
        /*if (cidStr !=null || cidStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{

            currentPage=1;
        }*/
        //每页显示条目，如果不传递默认为5条
        int pageSize=this.parseInt(pageSizeStr,5);
        /*if (cidStr !=null || cidStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;
        }*/


        //调用service，查询数据，并将数据序列化返回客户端
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize,rname);
        //数据序列化
        writeValue(routePageBean,response);
    }




}
