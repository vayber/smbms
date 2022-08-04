<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>
 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户信息查看页面</span>
        </div>
        <div class="providerView">
            <table border="5" cellspacing="5" cellpadding="8"  style="width: 100%">
                <tr>
                    <th>用户ID：</th>
                    <td style="width: 35%">${user.id }</td>
                    <th>用户名称：</th>
                    <td style="width: 35%">${user.userName } </td>
                </tr>
                <tr>
                    <th>用户编号：</th>
                    <td>${user.userCode }</td>
                    <th>用户密码：</th>
                    <td>${user.userPassword } </td>
                </tr>
                <tr>
                    <th>用户年龄：</th>
                    <td>${user.age }</td>
                    <th>用户性别：</th>
                    <td>${user.userGender} </td>
                </tr>
                <tr>
                    <th>出生日期：</th>
                    <td>${user.birthday }</td>
                    <th>用户电话：</th>
                    <td>${user.phone} </td>
                </tr>
                <tr>
                    <th>用户地址：</th>
                    <td>${user.address }</td>
                    <th>用户角色：</th>
                    <td>${user.roleName} </td>
                </tr>
                <tr>
                    <th>createdBy：</th>
                    <td>${user.createdBy }</td>
                    <th>creationDate：</th>
                    <td>${user.creationDate }</td>
                </tr>
                <tr>
                    <th>modifyBy：</th>
                    <td>${user.modifyBy }</td>
                    <th>modifyDate：</th>
                    <td>${user.modifyDate }</td>
                </tr>



            </table>

			<div class="providerAddBtn" style="text-align: center">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>
    </div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userview.js"></script>