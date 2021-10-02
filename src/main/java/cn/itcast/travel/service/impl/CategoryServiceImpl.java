package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao  categoryDao=new CategoryDaoImpl();


    @Override
    public List<Category> findCategory() {
        List<Category> cs = null;
        //从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);

        //判断数据是否为空
        if (category==null || category.size()==0){
            //将数据从数据库中查找出来，并存入redis
            System.out.println("从数据库中查询。。。");
            cs=categoryDao.findAll();
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());

            }



        }else {
            System.out.println("从redise中查询。。。");
            //如果不为空，将数据转换为list
            cs=new ArrayList<Category>();
            for (Tuple s : category) {
                Category c = new Category();
                c.setCname(s.getElement());
                c.setCid((int)s.getScore());
                cs.add(c);
            }
        }


        return cs;
    }
}
