package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouterDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
          private RouteDao dao=new RouterDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装对象
        PageBean<Route> pageBean = new PageBean<Route>();
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页显示的条数
        pageBean.setPageSize(pageSize);
        //获取页面的总数
        int TotalCuont=dao.findTotalCount(cid,rname);
        pageBean.setTotalCount(TotalCuont);
        //设置当前页面显示的数据集合
        int start=(currentPage-1)*pageSize;
        List<Route> list=dao.findbyPage(cid,start,pageSize,rname);
        pageBean.setList(list);

        //设置总页数
        int totalPage=TotalCuont%pageSize==0 ? TotalCuont/pageSize : (TotalCuont/pageSize)+1;
        pageBean.setTotalPage(totalPage);

        return pageBean;
    }
}
