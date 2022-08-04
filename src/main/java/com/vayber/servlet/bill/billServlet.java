package com.vayber.servlet.bill;

import com.alibaba.fastjson.JSONArray;
import com.vayber.pojo.Bill;
import com.vayber.pojo.Provider;
import com.vayber.services.Bill.billServicesImpl;
import com.vayber.services.Provider.providerServicesImpl;
import com.vayber.servlet.BaseServlet;
import com.vayber.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/billManagement.do")
public class billServlet extends BaseServlet {

    public void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //从前端获取数据
        String queryProductName = req.getParameter("queryProductName"); //查询商品
        String queryProvider = req.getParameter("queryProvider");       //查询供应商
        String queryIsPayment = req.getParameter("queryIsPayment");     //查询是否付款
        String pageIndex = req.getParameter("pageIndex");       //当前页

        //billlist.jsp页面供应商查询id
        int queryProviderId = 0;
        int queryIsPaymentNum = 0;

        //获取订单列表
        billServicesImpl billServices = new billServicesImpl();
        List<Bill> billList = null;

        //第一次走这个请求一定是第一页并且页面大小是固定的
        int pageSize = 5;   //一般把这个写到配置文件 方便后期修改
        int currentPageNo = 1;

        if (queryProductName == null){  //给查询赋值
            queryProductName = "";
        }
        //   int queryProviderId = 0  下拉框不选择默认是0 如果选了就走下面if 赋选择的值
        if (queryProvider != null && !queryProvider.equals("")){
            queryProviderId = Integer.parseInt(queryProvider);
        }
        if (queryIsPayment != null && !queryIsPayment.equals("")){
            queryIsPaymentNum = Integer.parseInt(queryIsPayment);
        }
        if (pageIndex != null){ //默认第一页
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取账单总数 (分页 上一页，下一页的情况)
        int totalCount = billServices.getBillCount(queryProductName, queryProviderId, queryIsPaymentNum);


        //总页数支持
        PageSupport pageSupport = new PageSupport();

        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //总页数
        int totalPageCount = pageSupport.getTotalPageCount();


        //控制首页和尾页
        //如果页面要小于1了 就显示第一页
        if (totalPageCount < 1){
            totalPageCount = 1;
        }else if (currentPageNo > totalPageCount)//当前页面大于最后一页
        {
            currentPageNo = totalPageCount;
        }

        //获取账单列表展示
         billList = billServices.getBillList(queryProductName, queryProviderId, queryIsPaymentNum, currentPageNo, pageSize);



        req.setAttribute("billList",billList);

        //获取供应商列表
        providerServicesImpl providerServices = new providerServicesImpl();
        List<Provider> providerList = providerServices.getProviderList();


        req.setAttribute("providerList",providerList);

        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);

        req.setAttribute("queryProductName",queryProductName);
        req.setAttribute("queryIsPaymentNum",queryIsPaymentNum);
        req.setAttribute("queryProviderId",queryProviderId);

        //返回前端
        req.getRequestDispatcher("jsp/billlist.jsp").forward(req,resp);

    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        String createdBy = req.getParameter("createdBy");
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");


        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDate = simpleDateFormat.format(date);

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(productCount);
        bill.setTotalPrice(totalPrice);
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setCreationDate(creationDate);
        bill.setCreatedBy(Long.parseLong(createdBy));

        billServicesImpl billServices = new billServicesImpl();
            billServices.addBill(bill);

        try {
            resp.sendRedirect(req.getContextPath()+"/billManagement.do?method=queryList");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String billId = req.getParameter("billid");

        HashMap<String, String> resultMap = new HashMap<String, String>();

        billServicesImpl billServices = new billServicesImpl();
        boolean b = billServices.deleteBill(Integer.parseInt(billId));

        if (b){
            resultMap.put("delResult","true");
        }else {
            resultMap.put("delResult","false");
        }

        //把resultMap转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(JSONArray.toJSONString(resultMap));
        writer.flush();
        writer.close();

    }

    public void modifyQuery(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

        String id = req.getParameter("billid");

        billServicesImpl billServices = new billServicesImpl();
        Bill bill = billServices.queryBill(Integer.parseInt(id));

        providerServicesImpl providerServices = new providerServicesImpl();
        List<Provider> providerList = providerServices.getProviderList();

        req.setAttribute("bill",bill);
        req.setAttribute("providerList",providerList);

        req.getRequestDispatcher("jsp/billmodify.jsp").forward(req,resp);
    }

    public void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        String id = req.getParameter("id");
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productDesc = req.getParameter("productDesc");
        String productUnit = req.getParameter("productUnit");
        String productCount = req.getParameter("productCount");
        String totalPrice = req.getParameter("totalPrice");
        String providerId = req.getParameter("providerId");
        String isPayment = req.getParameter("isPayment");
        String modifyBy = req.getParameter("modifyBy");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(date);

        Bill bill = new Bill();
        bill.setId(Long.parseLong(id));
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(productCount);
        bill.setTotalPrice(totalPrice);
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setModifyBy(Long.parseLong(modifyBy));
        bill.setModifyDate(modifyDate);


        billServicesImpl billServices = new billServicesImpl();
         billServices.modifyBill(bill);

        resp.sendRedirect(req.getContextPath()+"/billManagement.do?method=queryList");


    }

    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("billid");

        billServicesImpl billServices = new billServicesImpl();
        Bill bill = billServices.queryBill(Integer.parseInt(id));

        req.setAttribute("bill",bill);

        req.getRequestDispatcher("jsp/billview.jsp").forward(req,resp);
    }
}
