package com.vayber.services.Provider;

import com.vayber.pojo.Provider;
import com.vayber.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface providerServices {
    //获取供应商数量
    public int getProviderCount(String proCode,String proName);

    //获取供应商列表    订单管理页面下拉框
    public List<Provider> getProviderList();

    //获取供应商列表 分页
    public List<Provider> getProviderList(String proCode,String proName,int currentPageNo, int pageSize);

    //添加供应商
    public boolean addProvider( Provider provider) throws SQLException;

    //删除供应商
    public boolean deleteProvider(int id) throws  SQLException;

    //修改供应商
    public boolean modifyProvider(Provider provider) throws SQLException;

    //查询供应商信息
    public Provider getQueryProvider(int id) throws SQLException;

}
