package com.vayber.dao.Bill;

import com.vayber.pojo.Bill;
import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillDao {
    //获取商品数量(根据商品名或者供应商或者是否付款)
    public  int getBillCount(Connection conn, String billName, int providerId,int isPayment) throws SQLException;

    //通过条件查询获取账单列表 搜索框
    public List<Bill> getBillList(Connection conn, String billName, int providerId,int isPayment, int currentPageNo, int pageSize) throws  SQLException;

    //添加订单
    public int addBill(Connection conn,Bill bill) throws  SQLException;

    //删除订单
    public int deleteBill(Connection conn,int id) throws  SQLException;

    //修改订单
    public int modifyBill(Connection conn,Bill bill) throws SQLException;

    //查询订单
    public Bill getQueryBill(Connection conn,int id) throws SQLException;

}
