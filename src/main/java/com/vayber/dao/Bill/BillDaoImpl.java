package com.vayber.dao.Bill;

import com.mysql.cj.util.StringUtils;
import com.vayber.dao.BaseDao;
import com.vayber.pojo.Bill;
import com.vayber.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao{

    public int getBillCount(Connection conn, String billName, int providerId, int isPayment) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int count = 0;

        if (conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(*) COUNT FROM smbms_bill b,smbms_provider p WHERE b.providerId = p.id");
            ArrayList<Object> list = new ArrayList<Object>();                   //存放参数

            if (!StringUtils.isNullOrEmpty(billName)) {
                sql.append(" and b.productName like ?");    //模糊查询
                list.add("%" + billName + "%");     //index:0
            }

            if (providerId > 0) {
                sql.append(" and b.providerId = ?");
                list.add(providerId); //index:1
            }

            if (isPayment > 0) {
                sql.append(" and b.isPayment = ?");
                list.add(isPayment); //index:2
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


    public List<Bill> getBillList(Connection conn, String billName, int providerId, int isPayment, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bill> billList = new ArrayList<Bill>();

        if (conn != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT b.*,p.proName FROM smbms_bill b,smbms_provider p WHERE b.providerId = p.id");

            ArrayList<Object> list = new ArrayList<Object>();   //存放参数

            if (!StringUtils.isNullOrEmpty(billName)) {
                sql.append(" and b.productName like ?");    //模糊查询
                list.add("%" + billName + "%");     //index:0
            }

            if (providerId > 0) {
                sql.append(" and b.providerId = ?");
                list.add(providerId); //index:1
            }

            if (isPayment > 0) {
                sql.append(" and b.isPayment = ?");
                list.add(isPayment); //index:2
            }

            sql.append(" order by id desc limit ? , ?");

            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            //把list转换为数组
            Object[] params = list.toArray();

            resultSet = BaseDao.execute(conn,preparedStatement,resultSet,sql.toString(),params);


            while (resultSet.next()){
                Bill bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setBillCode(resultSet.getString("billCode"));
                bill.setProductName(resultSet.getString("productName"));
                bill.setProductDesc(resultSet.getString("productDesc"));
                bill.setProductUnit(resultSet.getString("productUnit"));
                bill.setProductCount(resultSet.getString("productCount"));
                bill.setTotalPrice(resultSet.getString("totalPrice"));
                bill.setIsPayment(resultSet.getInt("isPayment"));
                bill.setIsPayments(resultSet.getInt("isPayment"));
                bill.setCreatedBy(resultSet.getInt("createdBy"));
                bill.setCreationDate(resultSet.getString("creationDate"));
                bill.setModifyBy(resultSet.getInt("modifyBy"));
                bill.setModifyDate(resultSet.getString("modifyDate"));
                bill.setProviderId(resultSet.getInt("providerId"));
                bill.setProName(resultSet.getString("proName"));

                billList.add(bill);
            }

            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return billList;
    }

    public int addBill(Connection conn, Bill bill) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null){
            String sql = "insert into smbms_bill (billCode,productName,productDesc,productUnit," +
                    "productCount,totalPrice,isPayment,providerId,creationDate,createdBy) values(?,?,?,?,?,?,?,?,?,?)";

            Object params[] ={bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),bill.getProductUnit(),
                    bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),bill.getProviderId(),
                    bill.getCreationDate(),bill.getCreatedBy()
            };

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);


        }
        BaseDao.closeResource(null,preparedStatement,null);


            return executeRow;
    }

    public int deleteBill(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "delete from smbms_bill where id = ?";

            Object params[] = {id};

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;

    }

    public int modifyBill(Connection conn, Bill bill) throws SQLException {
        PreparedStatement preparedStatement = null;
        int executeRow = 0;

        if (conn != null) {
            String sql = "update smbms_bill set billCode = ?,productName = ?,productDesc = ?," +
                    "productUnit = ?,productCount = ?,totalPrice = ?," +
                    "isPayment = ?,providerId = ?,modifyDate = ?,modifyBy = ?  where id = ?";

            Object params[] = {bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),bill.getProductUnit(),
                    bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),bill.getProviderId(),
                   bill.getModifyDate(), bill.getModifyBy(),bill.getId()
            };

            executeRow = BaseDao.execute(conn, preparedStatement, sql, params);
        }

        BaseDao.closeResource(null,preparedStatement,null);

        return executeRow;
    }

    public Bill getQueryBill(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Bill bill = null;

        if (conn != null) {
            String sql = "select b.*,p.proName from smbms_bill b,smbms_provider p where b.providerId = p.id and b.id = ? ";

            Object[] params = {id};

            resultSet = BaseDao.execute(conn, preparedStatement, resultSet, sql, params);

            if (resultSet.next()){
                bill = new Bill();
                bill.setId(resultSet.getInt("id"));
                bill.setBillCode(resultSet.getString("billCode"));
                bill.setProductName(resultSet.getString("productName"));
                bill.setProductDesc(resultSet.getString("productDesc"));
                bill.setProductUnit(resultSet.getString("productUnit"));
                bill.setProductCount(resultSet.getString("productCount"));
                bill.setTotalPrice(resultSet.getString("totalPrice"));
                bill.setIsPayment(resultSet.getInt("isPayment"));
                bill.setIsPayments(resultSet.getInt("isPayment"));
                bill.setCreatedBy(resultSet.getInt("createdBy"));
                bill.setCreationDate(resultSet.getString("creationDate"));
                bill.setModifyBy(resultSet.getInt("modifyBy"));
                bill.setModifyDate(resultSet.getString("modifyDate"));
                bill.setProviderId(resultSet.getInt("providerId"));
                bill.setProName(resultSet.getString("proName"));

            }

        }
        BaseDao.closeResource(null,preparedStatement,resultSet);
        return bill;
    }
}
