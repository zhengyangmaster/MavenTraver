package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findBycode(String code) ;

    public void updateStatus(User user) ;

    //根据用户名查询
    public User findByUsername(String username);
    //用户添加
    public void save(User user);

    User findByUsernameAndPassword(String username, String password);
}
