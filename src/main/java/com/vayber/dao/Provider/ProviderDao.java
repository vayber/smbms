package com.vayber.dao.Provider;

import com.vayber.pojo.Bill;
import com.vayber.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
    //获取供应商总数(根据供应商编码或者供应商名称)
    public int getProviderCount(Connection conn, String proCode, String proName) throws SQLException;

    //获取供应商列表
    public List<Provider> getProviderList(Connection conn) throws SQLException;

    //分页获取供应商列表
    public List<Provider> getProviderList(Connection connection, String proCode, String proName, int currentPageNo, int pageSize) throws SQLException;

    //添加供应商
    public int addProvider(Connection conn, Provider provider) throws  SQLException;

    //删除供应商
    public int deleteProvider(Connection conn,int id) throws  SQLException;

    //修改供应商
    public int modifyProvider(Connection conn,Provider provider) throws SQLException;

    //查询供应商信息
    public Provider getQueryProvider(Connection conn,int id) throws SQLException;


}
