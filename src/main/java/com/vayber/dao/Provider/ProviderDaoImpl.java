package com.vayber.dao.Provider;


import com.mysql.cj.util.StringUtils;
import com.vayber.dao.BaseDao;
import com.vayber.pojo.Bill;
import com.vayber.pojo.Provider;
import com.vayber.pojo.Role;
import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao{
    public int getProviderCount(Connection conn, String proCode, String proName) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;
        if (conn != null){
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(*) COUNT FROM smbms_provider p ");

            ArrayList<Object> list = new ArrayList<Object>();                   //存放参数

            if (!StringUtils.isNullOrEmpty(proCode)){
                sql.append(" where p.proCode like ?");    //模糊查询
                list.add("%"+proCode+"%");     //index:0
            }
            if (!StringUtils.isNullOrEmpty(proName)) {
                if (StringUtils.isNullOrEmpty(proCode)) {    //如果条件1为空

                    sql.append(" where p.proName like ?");
                    list.add("%" + proName + "%");
                } else {                                     //如果条件1不为空
                    sql.append(" and p.proName like ?");
                    list.add("%" + proName + "%");      //index:1
                }
            }
            //把list转换为数组
            Object[] params = list.toArray();

            resultSet = BaseDao.execute(conn,preparedStatement,resultSet,sql.toString(),params);

            if (resultSet.next()){
                count = resultSet.getInt("count");
            }

        }

        BaseDao.closeResource(null,preparedStatement,resultSet);

        return count;
    }

    public List<Provider> getProviderList(Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Provider> providerList = new ArrayList<Provider>();


        if (conn != null) {
            String sql = "select * from smbms_provider";
            Object[] params = {};

            resultSet = BaseDao.execute(conn, preparedStatement, resultSet, sql, params);

            while (resultSet.next()){
                Provider provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getString("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getString("modifyDate"));

                providerList.add(provider);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);

        }



            return providerList;
    }

    public List<Provider> getProviderList(Connection connection,String proCode, String proName, int currentPageNo, int pageSize)throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Provider> providerList = new ArrayList<Provider>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_provider p ");

            ArrayList<Object> list = new ArrayList<Object>();   //存放参数

            if (!StringUtils.isNullOrEmpty(proCode)){
                sql.append(" where p.proCode like ?");    //模糊查询
                list.add("%"+proCode+"%");     //index:0
            }
            if (!StringUtils.isNullOrEmpty(proName)) {
                if (StringUtils.isNullOrEmpty(proCode)) {    //如果条件1为空
                    sql.append(" where p.proName like ?");
                    list.add("%" + proName + "%");
                } else {                                     //如果条件1不为空
                    sql.append(" and p.proName like ?");
                    list.add("%" + proName + "%");      //index:1
                }
            }

            sql.append(" order by id DESC limit ? , ?");

            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            //把list转换为数组
            Object[] params = list.toArray();

            resultSet = BaseDao.execute(connection,preparedStatement,resultSet,sql.toString(),params);

            while (resultSet.next()){
                Provider provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getString("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getString("modifyDate"));

                providerList.add(provider);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }

        return providerList;
    }

    public int addProvider(Connection conn, Provider provider) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null){
            String sql = "insert into smbms_provider (proCode,proName,proDesc,proContact," +
                    "proPhone,proAddress,proFax,createdBy,creationDate," +
                    "modifyDate,modifyBy) values (?,?,?,?,?,?,?,?,?,?,?)";

            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProDesc(),
            provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),
            provider.getCreatedBy(),provider.getCreationDate(),provider.getModifyDate(),provider.getModifyBy()
            };

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }
        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public int deleteProvider(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "delete from smbms_provider where id = ?";

            Object params[] = {id};

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public int modifyProvider(Connection conn, Provider provider) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "update smbms_provider set proCode = ?,proName = ?," +
                    "proDesc = ?,proContact = ?,proPhone = ?,proAddress = ?,proFax = ?," +
                    "modifyDate = ?,modifyBy = ? where id = ?";

            Object[] params = {
                    provider.getProCode(),provider.getProName(),provider.getProDesc(),
                    provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
                    provider.getProFax(), provider.getModifyDate(),provider.getModifyBy(), provider.getId()
            };


            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public Provider getQueryProvider(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Provider provider = null;

        if (conn != null) {
            String sql = "select * from smbms_provider where id = ?";

            Object[] params = {id};

            resultSet = BaseDao.execute(conn, preparedStatement, resultSet, sql, params);

            if (resultSet.next()){
                 provider = new Provider();
                provider.setId(resultSet.getInt("id"));
                provider.setProCode(resultSet.getString("proCode"));
                provider.setProName(resultSet.getString("proName"));
                provider.setProDesc(resultSet.getString("proDesc"));
                provider.setProContact(resultSet.getString("proContact"));
                provider.setProPhone(resultSet.getString("proPhone"));
                provider.setProAddress(resultSet.getString("proAddress"));
                provider.setProFax(resultSet.getString("proFax"));
                provider.setCreatedBy(resultSet.getInt("createdBy"));
                provider.setCreationDate(resultSet.getString("creationDate"));
                provider.setModifyBy(resultSet.getInt("modifyBy"));
                provider.setModifyDate(resultSet.getString("modifyDate"));
            }
        }
        BaseDao.closeResource(null,preparedStatement,resultSet);

        return provider;
    }
}
