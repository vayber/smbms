<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
<%--<%--%>
<%--    Object userRoleItem = session.getAttribute("userRoleItem");--%>
<%--    if(userRoleItem == null){--%>
<%--        response.sendRedirect("/smbms/role/getRoleList.do");--%>
<%--    }--%>
<%--%>--%>
<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/management.do">
                <input type="hidden" name="method" value="add">
                <input type="hidden" name="createdBy" value="${userSession.id}">
                <div>
                    <label for="userCode">用户编码：</label>
                    <input type="text" name="userCode" id="userCode" value=""> 
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="userName" id="userName" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="userPassword">用户密码：</label>
                    <input type="text" name="userPassword" id="userPassword" value="">
					<font color="red"></font>
                </div>
                <div>
                    <label for="ruserPassword">用户年龄：</label>
                    <input type="text" name="userAge" id="ruserPassword" value="">
					<font color="red"></font>
                </div>
                <div>
                    <label >用户性别：</label>
					<select name="gender" id="gender">
					    <option value="1" selected="selected">女</option>
					    <option value="2">男</option>
					 </select>

                </div>
                <div>
                    <label for="birthday">出生日期：</label>
                    <input type="date" Class="Wdate" id="birthday" name="birthday">
                    <font color="red"></font>
                </div>
                <div>
                    <label for="phone">用户电话：</label>
                    <input type="text" name="phone" id="phone" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="address">用户地址：</label>
                   <input name="address" id="address"  value="">
                </div>
                <div>
                    <label >用户角色：</label>
                    <!-- 列出所有的角色分类 -->

                    <select name="queryUserRole">
                        <option value="0"
                                <c:if test="${queryUserRole == '0'}">
                                    selected
                                </c:if>
                        >--请选择--</option>
                        <option value="1"
                                <c:if test="${queryUserRole == '1'}">
                                    selected
                                </c:if>
                        >系统管理员</option>
                        <option value="2"
                                <c:if test="${queryUserRole == '2'}">
                                    selected
                                </c:if>
                        >经理</option>
                        <option value="3"
                                <c:if test="${queryUserRole == '3'}">
                                    selected
                                </c:if>
                        >普通员工</option>
                    </select>


	        		<font color="red"></font>
                </div>
                <div class="providerAddBtn">
                    <input type="button" name="add" id="add" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form>
        </div>
</div>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/useradd.js"></script>
