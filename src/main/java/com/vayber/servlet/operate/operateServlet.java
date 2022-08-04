package com.vayber.servlet.operate;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.vayber.pojo.User;
import com.vayber.services.user.userServices;
import com.vayber.services.user.userServicesImpl;
import com.vayber.servlet.BaseServlet;
import com.vayber.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/operate.do")
public class operateServlet extends BaseServlet {

    //登录
    public void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库进行匹配 调用业务层
        userServices userServices = new userServicesImpl();

        User user = userServices.Login(userCode, userPassword);    //如果查不到返回值为null

        if (user != null){      //查到了
            //将用户信息放入session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到主页
            resp.sendRedirect("jsp/frame.jsp");

        }else {         //没查到
            //转发回登录界面，顺带告诉它：用户名或者密码错误
            req.setAttribute("error","用户名或者密码错误！");
            req.getRequestDispatcher("login.jsp").forward(req,resp);

        }
    }

    //退出
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //移除session
        req.getSession().removeAttribute(Constants.USER_SESSION);

        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }

    //验证旧密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从session里面拿id
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        String oldpassword = req.getParameter("oldpassword");       //从ajax里面传的参数

        Map<String, String> resultMap = new HashMap<String, String>();

        if (o == null){     //session失效了 过期了
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){  //输入密码为空
            resultMap.put("result","error");
        }else{
            String userPassword = ((User) o).getUserPassword();
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }



        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JsonArray 阿里巴巴json工具类     将map转换为json格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改密码
    public void savePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session里面拿id
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        String newPassword = req.getParameter("newpassword");



        boolean flag = false;

        if (o != null && !StringUtils.isNullOrEmpty(newPassword)){

            userServices userServices = new userServicesImpl();
            flag = userServices.updateUserPassword((int) ((User) o).getId(), newPassword);

            if (flag){  //修改成功
                req.setAttribute("message","修改成功，请退出使用新密码登录！");

                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else{
                req.setAttribute("message","密码修改失败！");
            }


        }else{
            req.setAttribute("message","新密码设置有问题！");
        }

        req.getRequestDispatcher("jsp/pwdmodify.jsp").forward(req,resp);
    }

}
