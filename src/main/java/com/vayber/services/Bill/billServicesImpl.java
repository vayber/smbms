package com.vayber.services.Bill;

import com.vayber.dao.BaseDao;
import com.vayber.dao.Bill.BillDao;
import com.vayber.dao.Bill.BillDaoImpl;
import com.vayber.dao.User.UserDao;
import com.vayber.dao.User.UserDaoImpl;
import com.vayber.pojo.Bill;
import com.vayber.pojo.Provider;
import com.vayber.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class billServicesImpl implements billServices{
    //业务层都要调用dao层，所以要引入Dao
    private BillDao billDao;

    public  billServicesImpl(){
        //创建userServicesImpl实例时，顺便实例化userDao
        billDao = new BillDaoImpl();
    }

    public int getBillCount(String billName, int providerId, int isPayment) {
        Connection connection = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            count = billDao.getBillCount(connection,billName,providerId,isPayment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }

    public List<Bill> getBillList(String billName, int providerId, int isPayment, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<Bill> billList = null;


        try {
            connection = BaseDao.getConnection();
          billList = billDao.getBillList(connection,billName,providerId,isPayment,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return billList;
    }

    public boolean addBill(Bill bill) {
        boolean flag = true;
        Connection connection = null;


        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务
            int updateRows = billDao.addBill(connection, bill);
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

    public boolean deleteBill(int id) {
        boolean flag = true;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = billDao.deleteBill(connection, id);

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

    public boolean modifyBill(Bill bill) {
        boolean flag = true;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);    //开启事务

            int updateRows = billDao.modifyBill(connection,bill);

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

    public Bill queryBill(int id) {
        Connection connection = null;
        Bill bill = null;


        try {
            connection = BaseDao.getConnection();
            bill = billDao.getQueryBill(connection,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return bill;
    }


    @Test
    public void test(){
        billServicesImpl billServices = new billServicesImpl();

        List<Bill> billList = billServices.getBillList(null, 0, 0, 1, 5);

        for (Bill bill : billList) {
            System.out.println(bill);
        }
    }
}
