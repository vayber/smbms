package com.vayber.services.Provider;

import com.vayber.dao.BaseDao;
import com.vayber.dao.Provider.ProviderDao;
import com.vayber.dao.Provider.ProviderDaoImpl;
import com.vayber.dao.Role.RoleDao;
import com.vayber.dao.Role.RoleDaoImpl;
import com.vayber.pojo.Provider;
import com.vayber.pojo.Role;
import com.vayber.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class providerServicesImpl implements providerServices{
    //引入dao
    private ProviderDao providerDao;

    public providerServicesImpl(){
        providerDao = new ProviderDaoImpl();
    }


    public int getProviderCount(String proCode, String proName) {
        Connection connection = null;
        int count = 0;


        try {
            connection = BaseDao.getConnection();
            count = providerDao.getProviderCount(connection,proCode,proName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }

    public List<Provider> getProviderList() {
        Connection connection = null;
        List<Provider> providerList = null;


        try {
            connection = BaseDao.getConnection();
          providerList = providerDao.getProviderList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return providerList;
    }

    public List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<Provider> providerList = null;


        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.getProviderList(connection, proCode, proName, currentPageNo, pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }

        return providerList;
    }

    public boolean addProvider(Provider provider) throws SQLException {
        boolean flag = true;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务
            int updateRows = providerDao.addProvider(connection,provider);
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

    public boolean deleteProvider(int id) throws SQLException {
        boolean flag = true;
        Connection connection = null;



        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = providerDao.deleteProvider(connection,id);

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

    public boolean modifyProvider(Provider provider) throws SQLException {
        boolean flag = true;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = providerDao.modifyProvider(connection,provider);

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

    public Provider getQueryProvider(int id) throws SQLException {
        Connection connection = null;
        Provider provider = null;


        try {
            connection = BaseDao.getConnection();
            provider = providerDao.getQueryProvider(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return provider;
    }


    @Test
    public void test(){
        providerServicesImpl providerServices = new providerServicesImpl();
        List<Provider> providerList = providerServices.getProviderList("BJ", null, 1, 5);
        System.out.println(providerList);

    }
}
