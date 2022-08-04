package com.vayber.dao.User;

import com.vayber.pojo.Role;
import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection conn, String userCode,String userPassword) throws SQLException;

    //修改用户密码
    public int updateUserPassword(Connection conn, int id, String password) throws  SQLException;

    //获取用户数量(根据用户名或者角色)
    public  int getUserCount(Connection conn, String username, int userRole) throws  SQLException;

    //通过条件查询获取用户列表 搜索框
    public List<User> getUserList(Connection conn, String username, int userRole,int currentPageNo,int pageSize) throws  SQLException;

    //增加用户
    public int addUser(Connection conn,User user) throws  SQLException;

    //删除用户
    public int deleteUser(Connection conn,int id) throws  SQLException;

    //修改用户
    public int modifyUser(Connection conn,User user) throws SQLException;

    //查询用户
    public User getQueryUser(Connection conn,int id) throws SQLException;


}
