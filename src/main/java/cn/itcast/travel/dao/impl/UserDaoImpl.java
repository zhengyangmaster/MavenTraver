package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {


    private JdbcTemplate Template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findBycode(String code) {
        //根据激活码查找对象
        User user = null;

        try {
            String sql = "select * from tab_user where code = ?";
            user = Template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {

        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        //修改用户激活状态
        String sql="update tab_user set status='Y' where uid=?";
        Template.update(sql,user.getUid());



    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        //定义sql语句
        try {
            String sql = "select * from tab_user where username = ?";
            //执行sql
            user = Template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (Exception e) {

        }


        return user;
    }

    @Override
    public void save(User user) {
        //定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                "values(?,?,?,?,?,?,?,?,?)";
        //执行sql
        Template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),
                user.getStatus(),user.getCode());

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        //定义sql语句
        try {
            String sql = "select * from tab_user where username = ? and password= ?";
            //执行sql
            user = Template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username,password);
        } catch (Exception e) {

        }


        return user;


    }
}
