package com.vayber.servlet.Provider;

import com.alibaba.fastjson.JSONArray;
import com.vayber.pojo.Provider;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/providerManagement.do")
public class providerServlet extends BaseServlet {

    //订单页面获取供应商名称列表
    public void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        providerServicesImpl providerServices = new providerServicesImpl();
        List<Provider> providerList = providerServices.getProviderList();
        req.setAttribute("providerList",providerList);

        //返回前端
        req.getRequestDispatcher("jsp/billadd.jsp").forward(req,resp);


    }

    //供应商页面获取供应商列表（分页）
    public void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //从前端获取数据
        String queryProCode = req.getParameter("queryProCode"); //查询供应商编码
        String queryProName = req.getParameter("queryProName"); //查询供应商名称
        String pageIndex = req.getParameter("pageIndex");       //当前页

        //第一次走这个请求一定是第一页并且页面大小是固定的
        int pageSize = 5;   //一般把这个写到配置文件 方便后期修改
        int currentPageNo = 1;


        //给查询赋值
        if (queryProCode == null){
            queryProCode = "";
        }
        if (queryProName == null){
            queryProName = "";
        }

        //默认第一页
        if (pageIndex != null){ //如果不是第一页时
            currentPageNo = Integer.parseInt(pageIndex);
        }





        providerServicesImpl providerServices = new providerServicesImpl();

        //获取供应商列表
        List<Provider> providerList = providerServices.getProviderList(queryProCode,queryProName,currentPageNo,pageSize);

        //获取供应商总数
        int totalCount = providerServices.getProviderCount(queryProCode, queryProName);


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



        req.setAttribute("providerList",providerList);

        req.setAttribute("totalCount",totalCount);

        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("currentPageNo",currentPageNo);

        req.setAttribute(" queryProCode", queryProCode);
        req.setAttribute("queryProName",queryProName);


        req.getRequestDispatcher("jsp/providerlist.jsp").forward(req,resp);


    }

    //添加供应商
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proDesc = req.getParameter("proDesc");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String createdBy = req.getParameter("createdBy");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDate = simpleDateFormat.format(date);

        Provider provider = new Provider();
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProDesc(proDesc);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setCreatedBy(Long.parseLong(createdBy));
        provider.setCreationDate(creationDate);

        providerServicesImpl providerServices = new providerServicesImpl();
        try {
            providerServices.addProvider(provider);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath()+"/providerManagement.do?method=queryList");
    }

    //删除供应商
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String proid = req.getParameter("proid");

        HashMap<String, String> resultMap = new HashMap<String, String>();

        providerServicesImpl providerServices = new providerServicesImpl();
        boolean b = false;
        try {
            b = providerServices.deleteProvider(Integer.parseInt(proid));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


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

    //修改查询
    public void modifyQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("proid");
        providerServicesImpl providerServices = new providerServicesImpl();
        try {
            Provider provider = providerServices.getQueryProvider(Integer.parseInt(id));

            req.setAttribute("provider",provider);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        req.getRequestDispatcher("jsp/providermodify.jsp").forward(req,resp);
    }
    //修改供应商
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("id");
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proDesc = req.getParameter("proDesc");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String modifyBy = req.getParameter("modifyBy");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(date);

        Provider provider = new Provider();
        provider.setId(Long.parseLong(id));
        provider.setProCode(proCode);
        provider.setProName(proName);
        provider.setProDesc(proDesc);
        provider.setProContact(proContact);
        provider.setProPhone(proPhone);
        provider.setProAddress(proAddress);
        provider.setProFax(proFax);
        provider.setModifyBy(Long.parseLong(modifyBy));
        provider.setModifyDate(modifyDate);

        providerServicesImpl providerServices = new providerServicesImpl();
        try {
            boolean b = providerServices.modifyProvider(provider);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath()+"/providerManagement.do?method=queryList");
    }

    //查询供应商
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("proid");

        providerServicesImpl providerServices = new providerServicesImpl();
        try {
            Provider provider = providerServices.getQueryProvider(Integer.parseInt(id));
            req.setAttribute("provider",provider);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        req.getRequestDispatcher("jsp/providerview.jsp").forward(req,resp);
    }
}
