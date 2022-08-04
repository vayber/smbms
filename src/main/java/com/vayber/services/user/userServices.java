package com.vayber.services.user;

import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface userServices {
    //用户登录
    public User Login(String userCode,String UserPassword);

    //根据用户id修改用户密码
    public boolean updateUserPassword( int id, String password) ;

    //查询符合条件的用户数量
    public int getUserCount(String userName,int userRole);

    //查询符合条件的用户列表
    public List<User> getUserList(String queryUsername, int queryUserRole,int currentPageNo, int pageSize);

    //增加用户
    public boolean addUser(User user);

    //删除用户
    public boolean deleteUser(int id);

    //修改用户
    public boolean modifyUser(User user);

    //查询用户
    public User queryUser(int id);
}
