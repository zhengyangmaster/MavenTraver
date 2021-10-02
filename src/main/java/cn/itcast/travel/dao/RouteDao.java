package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    public int findTotalCount(int cid, String rname);

    public List<Route> findbyPage(int cid, int start, int pageSize, String rname);
}
