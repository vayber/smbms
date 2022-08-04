package com.vayber.services.user;

import com.vayber.dao.BaseDao;
import com.vayber.dao.User.UserDao;
import com.vayber.dao.User.UserDaoImpl;
import com.vayber.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class userServicesImpl implements userServices{

    //业务层都要调用dao层，所以要引入Dao
    private UserDao userDao;

    public  userServicesImpl(){
        //创建userServicesImpl实例时，顺便实例化userDao
        userDao = new UserDaoImpl();
    }

    public User Login(String userCode, String userPassword) {
        Connection connection = null;
        User user = null;



        try {

            connection = BaseDao.getConnection();
            //通过业务层调用具体的对应的数据库操作
           user = userDao.getLoginUser(connection,userCode,userPassword);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }


        return user;
    }

    public boolean updateUserPassword(int id, String password) {
        boolean flag = false;
        Connection conn = null;

        try {
            conn = BaseDao.getConnection();

            if (userDao.updateUserPassword(conn,id,password)>0){

                flag = true;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return flag;

    }

    public int getUserCount(String userName, int userRole) {

        Connection connection = null;
        int count = 0;



        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection,userName,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }


    public List<User> getUserList(String queryUsername, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection,queryUsername,queryUserRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }   finally {
            BaseDao.closeResource(connection,null,null);
        }


        return  userList;
    }

    public boolean addUser(User user) {
            boolean flag = true;
            Connection connection = null;



        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务
            int updateRows = userDao.addUser(connection,user);
            connection.commit();

            if (updateRows > 0){
                flag = true;
                System.out.println("add success");

            }else {
                System.out.println("add failed");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection,null,null);
        }

        return flag;


    }

    public boolean deleteUser(int id) {
        boolean flag = true;
        Connection connection = null;



        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = userDao.deleteUser(connection, id);

            connection.commit();

            if (updateRows > 0){
                flag = true;
                System.out.println("delete success");

            }else {
                System.out.println("delete failed");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection,null,null);
        }

        return flag;
    }

    public boolean modifyUser(User user) {

        boolean flag = true;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = userDao.modifyUser(connection,user);

            connection.commit();

            if (updateRows > 0){
                flag = true;
                System.out.println("update success");

            }else {
                System.out.println("update failed");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection,null,null);
        }


        return flag;
    }

    public User queryUser(int id) {
        Connection connection = null;
        User user = null;


        try {
            connection = BaseDao.getConnection();
            user = userDao.getQueryUser(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }


    @Test
    public void Test(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(date);

        System.out.println(modifyDate);


       User user = new User();
        user.setModifyDate(modifyDate);
//        user.setModifyDate(date);
        System.out.println(user.getModifyDate());
//        user.setModifyDate(date1);
    }

}
