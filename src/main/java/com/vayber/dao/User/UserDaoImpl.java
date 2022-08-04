package com.vayber.dao.User;

import com.mysql.cj.util.StringUtils;
import com.vayber.dao.BaseDao;
import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    public User getLoginUser(Connection conn, String userCode,String userPassword) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (conn != null){
            String sql = "select * from smbms_user where userCode = ? and userPassword = ?";

            Object[] params = {userCode,userPassword};

                resultSet = BaseDao.execute(conn, preparedStatement,resultSet, sql, params);

                if (resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserCode(resultSet.getString("userCode"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setGender(resultSet.getInt("gender"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setUserRole(resultSet.getInt("userRole"));
                    user.setCreatedBy(resultSet.getInt("createdBy"));
                    user.setCreationDate(resultSet.getString("creationDate"));
                    user.setModifyBy(resultSet.getInt("modifyBy"));
                    user.setModifyDate(resultSet.getString("modifyDate"));
                }

                BaseDao.closeResource(null,preparedStatement,resultSet);



        }



        return user;
    }


    public int updateUserPassword(Connection conn, int id, String password) throws  SQLException{

        PreparedStatement preparedStatement = null;

        int execute = 0;

       if (conn != null){
           String sql = "update smbms_user set userPassword = ? where id = ?";
           Object params[] = {password,id};

            execute = BaseDao.execute(conn, preparedStatement, sql, params);

           BaseDao.closeResource(null,preparedStatement,null);
       }



        return execute;
    }


    public int getUserCount(Connection conn, String username, int userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (conn != null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(*) COUNT FROM smbms_user u,smbms_role r WHERE u.userRole = r.id");

            ArrayList<Object> list = new ArrayList<Object>();                   //存放参数

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");    //模糊查询
                list.add("%"+username+"%");     //index:0
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole); //index:1
            }

            //把list转换为数组
            Object[] params = list.toArray();



            resultSet = BaseDao.execute(conn,preparedStatement,resultSet,sql.toString(),params);

            if (resultSet.next()){
                count = resultSet.getInt("count");
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }

        return count;
    }


    public List<User> getUserList(Connection conn, String username, int userRole, int currentPageNo, int pageSize) throws SQLException {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            List<User> userList = new ArrayList<User>();

        if (conn != null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT u.*,r.roleName AS userRoleName FROM smbms_user u,smbms_role r WHERE u.userRole = r.id");

            ArrayList<Object> list = new ArrayList<Object>();   //存放参数

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");    //模糊查询
                list.add("%"+username+"%");     //index:0
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole); //index:1
            }

            sql.append(" order by id DESC limit ? , ?");

            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            //把list转换为数组
            Object[] params = list.toArray();



            resultSet = BaseDao.execute(conn,preparedStatement,resultSet,sql.toString(),params);

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setRoleName(resultSet.getString("userRoleName"));
                user.setAge(resultSet.getInt("age"));

                userList.add(user);
            }

            BaseDao.closeResource(null,preparedStatement,resultSet);
        }


        return userList;
    }


    public int addUser(Connection conn, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null){
            String sql = "insert into smbms_user (userCode,userName,userPassword,userRole,gender,age,birthday,phone,address,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?)";
            Object params[] = {user.getUserCode(),user.getUserName(),user.getUserPassword(),
                    user.getUserRole(),user.getGender(),user.getAge(),
                    user.getBirthday(), user.getPhone(),user.getAddress(),
                    user.getCreatedBy(),user.getCreationDate()
            };

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);

        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public int deleteUser(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "delete from smbms_user where id = ?";

            Object params[] = {id};

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);


        return executeRow;
    }

    public int modifyUser(Connection conn, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "update smbms_user set userName = ?,gender = ?,age = ?," +
                    "phone = ?,address = ?,userRole = ? ,modifyBy = ? ,modifyDate = ? where id = ?";

            Object params[] = {user.getUserName(),user.getGender(),user.getAge(),
                user.getPhone(),user.getAddress(),user.getUserRole(),
                user.getModifyBy(),user.getModifyDate(), user.getId()
            };

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public User getQueryUser(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (conn != null) {
            String sql = "select * from smbms_user where id = ?";
            Object[] params = {id};

            resultSet = BaseDao.execute(conn, preparedStatement,resultSet, sql, params);


            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setUserGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setRoleName(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getString("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getString("modifyDate"));
            }

        }
        BaseDao.closeResource(null,preparedStatement,resultSet);

        return user;
    }


}
