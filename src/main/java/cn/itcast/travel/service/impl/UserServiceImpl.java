package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    @Override
    public boolean regist(User user) {

        //根据用户名查找用户
        User u = dao.findByUsername(user.getUsername());
        if (u !=null){
            //用户名存在
            return  false;
        }
            //保存用户信息
        //设置用户激活码唯一字符串，设置激活状态
        user.setCode(UuidUtil.getUuid());

        user.setStatus("N");

        dao.save(user);
        //发送邮件内容
        String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'> 点击激活卢本伟广场</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    //激活用户
    @Override
    public boolean active(String code) {
        User user=dao.findBycode(code);
            if (user!=null){
                //调用激活方法
                dao.updateStatus(user);
                return true;
            }else {
                return false;
            }

    }

    @Override
    public User login(User user) {

        return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
