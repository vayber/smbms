package com.vayber.services.Bill;

import com.vayber.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface billServices {
    //获取账单数量
    public int getBillCount(String billName, int providerId, int isPayment);

    //获取账单列表
    public List<Bill> getBillList( String billName, int providerId, int isPayment, int currentPageNo, int pageSize);

    //添加订单
    public boolean addBill(Bill bill);

    //删除订单
    public boolean deleteBill(int id);

    //修改订单
    public boolean modifyBill(Bill bill);

    //查询订单
    public Bill queryBill(int id);
}
