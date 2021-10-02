package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RouterDaoImpl implements RouteDao {
    private JdbcTemplate Template= new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) {
        //String sql="select count(*) from tab_route where cid=?";
        String sql ="select count(*) from tab_route where 1 = 1";
        StringBuilder sb=new StringBuilder(sql);
        ArrayList parms = new ArrayList();
        if (cid !=0){
            sb.append(" and cid = ? ");
            parms.add(cid);
        }
        if (rname!=null ||rname.length()>0){
            sb.append(" and rname like ? ");
            parms.add("%"+rname+"%");
        }
        sql=sb.toString();
        Integer integer = Template.queryForObject(sql, Integer.class, parms.toArray());
        return integer;
    }

    @Override
    public List<Route> findbyPage(int cid, int start, int pageSize, String rname) {
       // String sql="select * from tab_route where ? limit ?,?";
        String sql="select * from tab_route where  1 = 1 ";

        StringBuilder sb=new StringBuilder(sql);
        List parms = new ArrayList();
        if (cid !=0){
            sb.append(" and cid = ? ");
            parms.add(cid);
        }
        if (rname!=null && rname.length()>0 && ! "undefined".equalsIgnoreCase(rname) && !"null".equalsIgnoreCase(rname)){
            sb.append(" and rname like ? ");
            parms.add("%"+rname+"%");
        }
        //分页条件
        sb.append(" limit ? , ? ");



        parms.add(start);
        parms.add(pageSize);
        sql=sb.toString();


        List<Route> query = Template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), parms.toArray());
        return query;
    }
}
