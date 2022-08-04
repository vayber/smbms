package com.vayber.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.vayber.pojo.Role;
import com.vayber.pojo.User;
import com.vayber.services.Role.roleServicesImpl;
import com.vayber.services.user.userServicesImpl;
import com.vayber.servlet.BaseServlet;
import com.vayber.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@WebServlet("/management.do")
public class userServlet extends BaseServlet {


    /*
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // 获取请求标识
            String methodName = req.getParameter("method");
            // 获取指定类的字节码对象
            Class<? extends BaseServlet> clazz = this.getClass();//这里的this指的是继承BaseServlet对象
            // 通过类的字节码对象获取方法的字节码对象
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 让方法执行
            method.invoke(this, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     */


    //查询用户列表
    public void queryList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRolesss");
        String pageIndex = req.getParameter("pageIndex");

        int queryUserRole = 0;

        //获取用户列表
        userServicesImpl userServices = new userServicesImpl();
        List<User> userList = null;

        //第一次走这个请求一定是第一页并且页面大小是固定的
        int pageSize = 5;   //一般把这个写到配置文件 方便后期修改
        int currentPageNo = 1;


        if (queryUserName == null){ //给查询赋值
            queryUserName = "";
        }
        //int queryUserRole = 0   下拉框不选择默认是0 如果选了就走下面if 赋选择的值
        if (temp != null && !temp.equals("")){   //给下拉框赋值
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex != null){ //默认第一页
            currentPageNo = Integer.parseInt(pageIndex);
        }


        //获取用户总数 (分页 上一页，下一页的情况)
        int totalCount = userServices.getUserCount(queryUserName, queryUserRole);

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

        //获取用户列表展示
        userList = userServices.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);


        req.setAttribute("userList",userList);

        //获取角色列表
        roleServicesImpl roleService = new roleServicesImpl();
        List<Role> roleList = roleService.getRoleList();

        req.setAttribute("roleList",roleList);

        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);


        //返回前端
        req.getRequestDispatcher("jsp/userlist.jsp").forward(req,resp);

    }

    //增加用户
    public void add(HttpServletRequest req, HttpServletResponse resp){

        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String userAge = req.getParameter("userAge");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String queryUserRole = req.getParameter("queryUserRole");
        String createdBy = req.getParameter("createdBy");


        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String creationDate = simpleDateFormat.format(date);

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAge(Integer.parseInt(userAge));
        user.setGender(Integer.parseInt(gender));
        user.setBirthday(Date.valueOf(birthday));
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(queryUserRole));
        user.setCreatedBy(Long.parseLong(createdBy));
        user.setCreationDate(creationDate);


        userServicesImpl userServices = new userServicesImpl();
        userServices.addUser(user);


        try {
            resp.sendRedirect(req.getContextPath()+"/management.do?method=queryList");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //删除用户
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uid = req.getParameter("uid");

        HashMap<String, String> resultMap = new HashMap<String, String>();

        userServicesImpl userServices = new userServicesImpl();
        boolean b = userServices.deleteUser(Integer.parseInt(uid));


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

    //查询修改用户
    public void modifyQuery(HttpServletRequest req, HttpServletResponse resp) {
        String uid = req.getParameter("uid");
        userServicesImpl userServices = new userServicesImpl();
        User user = userServices.queryUser(Integer.parseInt(uid));
        req.setAttribute("user",user);

        try {
            req.getRequestDispatcher("jsp/usermodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //修改用户
    public void modify(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");

        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String age = req.getParameter("age");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");
        String modifyBy = req.getParameter("modifyBy");

        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String modifyDate = simpleDateFormat.format(date);


        User user = new User();
        user.setId(Long.parseLong(id));
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        user.setAge(Integer.parseInt(age));
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.parseInt(userRole));
        user.setModifyBy(Long.parseLong(modifyBy));
        user.setModifyDate(modifyDate);


        userServicesImpl userServices = new userServicesImpl();
        userServices.modifyUser(user);

        try {
            resp.sendRedirect(req.getContextPath()+"/management.do?method=queryList");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //查询用户
    public void query(HttpServletRequest req, HttpServletResponse resp){
        String uid = req.getParameter("uid");
        userServicesImpl userServices = new userServicesImpl();
        User user = userServices.queryUser(Integer.parseInt(uid));
        req.setAttribute("user",user);
        try {
            req.getRequestDispatcher("jsp/userview.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
